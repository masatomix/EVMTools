package nu.mine.kino.jenkins.plugins.projectmanagement;

import hudson.Extension;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;
import nu.mine.kino.entity.Project;
import nu.mine.kino.jenkins.plugins.projectmanagement.utils.PMUtils;
import nu.mine.kino.projects.ACCreator;
import nu.mine.kino.projects.EVCreator;
import nu.mine.kino.projects.JSONProjectCreator;
import nu.mine.kino.projects.PVCreator;
import nu.mine.kino.projects.ProjectException;
import nu.mine.kino.projects.ProjectWriter;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Sample {@link Builder}.
 * 
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked and a new
 * {@link EVMToolsBuilder} is created. The created instance is persisted to the
 * project configuration XML by using XStream, so this allows you to use
 * instance fields (like {@link #name}) to remember the configuration.
 * 
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 * 
 * @author Masatomi KINO.
 */
public class EVMToolsBuilder extends Builder {

    private final String name;

    private static final String[] PREFIX_ARRAY = new String[] { "base_",
            "base1_", "base2_" };

    private final String addresses;

    private final boolean sendAll;

    // Fields in config.jelly must match the parameter names in the
    // "DataBoundConstructor"
    @DataBoundConstructor
    public EVMToolsBuilder(String name, String addresses, boolean sendAll) {
        this.name = name;
        this.addresses = addresses;
        this.sendAll = sendAll;
    }

    /**
     * We'll use this from the <tt>config.jelly</tt>.
     */
    public String getName() {
        return name;
    }

    public String getAddresses() {
        return addresses;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher,
            BuildListener listener) throws InterruptedException, IOException {

        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a
        // build.

        // This also shows how you can consult the global configuration of the
        // builder
        // if (getDescriptor().getUseFrench())
        // listener.getLogger().println("Bonjour, " + name + "!");
        // else
        // listener.getLogger().println("Hello, " + name + "!");
        //
        // Collection<User> all = User.getAll();

        FilePath root = build.getModuleRoot(); // ���[�N�X�y�[�X�̃��[�g
        FilePath buildRoot = new FilePath(build.getRootDir()); // ���̃r���h�̃��[�g
        listener.getLogger().println("[EVM Tools] JSON�t�@�C���쐬�J�n");
        FilePath pmJSON = executeAndCopies(root, buildRoot,
                new ProjectWriterExecutor(name));

        listener.getLogger().println("[EVM Tools] file:" + pmJSON);

        listener.getLogger().println("[EVM Tools] PV�t�@�C���쐬�J�n");
        executeAndCopy(root, buildRoot, new PVCreatorExecutor(name));
        listener.getLogger().println("[EVM Tools] AC�t�@�C���쐬�J�n");
        executeAndCopies(root, buildRoot, new ACCreatorExecutor(name));
        listener.getLogger().println("[EVM Tools] EV�t�@�C���쐬�J�n");
        executeAndCopies(root, buildRoot, new EVCreatorExecutor(name));

        // System.out.println(build.getModuleRoot());
        // System.out.println(build.getRootDir());
        // System.out.println(build.getWorkspace());
        // System.out.println(build.getArtifactsDir());

        ProjectSummaryAction action = PMUtils.getProjectSummaryAction(build);
        action.setFileName(pmJSON.getName());// target����??

        File json = new File(build.getRootDir(), pmJSON.getName());
        listener.getLogger().println(
                "[EVM Tools] Project :" + json.getAbsolutePath());

        Project project = null;
        try {
            project = new JSONProjectCreator(json).createProject();
        } catch (ProjectException e) {
            throw new IOException(e);
        }

        PMUtils.checkProjectAndMail(project, addresses, build, listener,sendAll);

        // testMethod(build, listener);
        return true;
    }

    // private void testMethod(AbstractBuild build, BuildListener listener)
    // throws IOException, InterruptedException {
    // try {
    // ExtensionList<TokenMacro> all = TokenMacro.all();
    // for (TokenMacro tokenMacro : all) {
    // listener.getLogger().println(tokenMacro.toString());
    // }
    //
    // List<TokenMacro> privateMacros = new ArrayList<TokenMacro>();
    // ClassLoader cl = Jenkins.getInstance().pluginManager.uberClassLoader;
    // for (final IndexItem<EmailToken, TokenMacro> item : Index.load(
    // EmailToken.class, TokenMacro.class, cl)) {
    // try {
    // privateMacros.add(item.instance());
    // } catch (Exception e) {
    // // ignore errors loading tokens
    // }
    // }
    // String expand = TokenMacro.expandAll(build, listener,
    // "$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!",
    // false, null);
    // listener.getLogger().println(expand);
    // expand = TokenMacro.expandAll(build, listener,
    // "Check console output at $BUILD_URL to view the results.",
    // false, null);
    // listener.getLogger().println(expand);
    // String BUILD_URL = (new StringBuilder())
    // .append(Hudson.getInstance().getRootUrl())
    // .append(build.getUrl()).toString();
    // String PROJECT_NAME = build.getProject().getName();
    // String BUILD_NUMBER = String.valueOf(build.getNumber());
    //
    // listener.getLogger()
    // .println(
    // String.format("%s - Build # %s", PROJECT_NAME,
    // BUILD_NUMBER));
    // listener.getLogger().println(
    // String.format(
    // "Check console output at %s to view the results.",
    // BUILD_URL));
    // } catch (MacroEvaluationException e1) {
    // // TODO �����������ꂽ catch �u���b�N
    // e1.printStackTrace();
    // }
    // }

    /**
     * root�ɑ΂���callable�ȏ��������s���A���ʃt�@�C����buildRoot�̉��ɔz�u����B
     * FilePath[]�̃f�[�^���擾�����[�J���ɃR�s�[�B �擪�̃|�C���^(FilePath)�����㑱�̏����Ŏg���̂ŁA�Ăь��ɕԋp�B
     * 
     * @param root
     * @param buildRoot
     * @param callable
     * @throws IOException
     * @throws InterruptedException
     */
    private FilePath executeAndCopies(FilePath root, FilePath buildRoot,
            FileCallable<FilePath[]> callable) throws IOException,
            InterruptedException {
        FilePath[] resultPaths = root.act(callable);

        for (FilePath resultPath : resultPaths) {
            if (resultPath != null) {
                FilePath targetPath = new FilePath(buildRoot,
                        resultPath.getName());
                resultPath.copyTo(targetPath); // remote�t�@�C�����A���[�J���ɃR�s�[�B�B
            }
        }
        return resultPaths[0];
    }

    private void executeAndCopy(FilePath root, FilePath buildRoot,
            FileCallable<FilePath> callable) throws IOException,
            InterruptedException {
        FilePath resultPath = root.act(callable);

        // FilePath returnPath = null;
        // for (FilePath resultPath : resultPaths) {
        FilePath targetPath = new FilePath(buildRoot, resultPath.getName());
        resultPath.copyTo(targetPath); // remote�t�@�C�����A���[�J���ɃR�s�[�B�B
        // if (returnPath == null) {
        // returnPath = targetPath;
        // }
        // }
        // return returnPath;
    }

    private static class ProjectWriterExecutor implements
            FileCallable<FilePath[]> {
        private final String fileName;

        public ProjectWriterExecutor(String fileName) {
            this.fileName = fileName;
        }

        public FilePath[] invoke(File f, VirtualChannel channel)
                throws IOException, InterruptedException {
            File target = new File(f, fileName);
            // String prefix = "base_";
            // File base = new File(target.getParentFile(), prefix
            // + target.getName()); // file����Prefix: base_������

            // File targetOutputFile = extracted(target);
            // File baseOutputFile = extracted(base);

            // //// Add ////
            // ���ł�JSON�t�@�C������������A���Ȃ��A�Ƃ��������B
            // if (targetOutputFile.exists() && baseOutputFile.exists()) {
            // return new FilePath[] { new FilePath(targetOutputFile),
            // new FilePath(baseOutputFile) };
            // }
            // //// Add ////
            try {
                List<FilePath> returnList = new ArrayList<FilePath>();
                File result = ProjectWriter.write(target);
                returnList.add(new FilePath(result));
                for (String base_prefix : PREFIX_ARRAY) {
                    File base = new File(target.getParentFile(), base_prefix
                            + target.getName());
                    if (base.exists()) {
                        FilePath result_base = new FilePath(
                                ProjectWriter.write(base));
                        returnList.add(result_base);
                    }
                }
                return returnList.toArray(new FilePath[returnList.size()]);
            } catch (ProjectException e) {
                throw new IOException(e);
            }
        }

        private File extracted(File target) {
            File baseDir = target.getParentFile();
            String output = target.getName() + "." + "json";
            File file = new File(baseDir, output);
            return file;
        }
    }

    private static class PVCreatorExecutor implements FileCallable<FilePath> {
        private final String fileName;

        public PVCreatorExecutor(String fileName) {
            this.fileName = fileName;
        }

        public FilePath invoke(File f, VirtualChannel channel)
                throws IOException, InterruptedException {
            File target = new File(f, fileName);
            // File base = new File(target.getParentFile(), "base_"
            // + target.getName()); // file����Prefix: base_������
            try {
                File jsonFile = new File(target.getParentFile(),
                        target.getName() + ".json");
                if (jsonFile.exists()) {
                    File result = PVCreator.createFromJSON(jsonFile);
                    return new FilePath(result);
                }
                File result = PVCreator.create(target);
                return new FilePath(result);

            } catch (ProjectException e) {
                throw new IOException(e);
            }
        }
    }

    private static class ACCreatorExecutor implements FileCallable<FilePath[]> {

        private final String fileName;

        public ACCreatorExecutor(String fileName) {
            this.fileName = fileName;
        }

        public FilePath[] invoke(File f, VirtualChannel channel)
                throws IOException, InterruptedException {
            File target = new File(f, fileName);
            try {
                FilePath[] results = executes(target, PREFIX_ARRAY);
                return results;
            } catch (ProjectException e) {
                throw new IOException(e);
            }
        }

        private FilePath[] executes(File target, String[] prefixArray)
                throws ProjectException {
            List<FilePath> returnList = new ArrayList<FilePath>();
            for (String base_prefix : prefixArray) {
                FilePath result = execute(target, base_prefix);
                returnList.add(result);
            }
            return returnList.toArray(new FilePath[returnList.size()]);
        }

        private FilePath execute(File target, String base_prefix)
                throws ProjectException {

            File jsonFile = new File(target.getParentFile(), target.getName()
                    + ".json");
            File jsonFile_base = new File(target.getParentFile(), base_prefix
                    + target.getName() + ".json");
            if (jsonFile_base.exists()) {
                File result = ACCreator.createFromJSON(jsonFile, jsonFile_base,
                        base_prefix);
                return new FilePath(result);
            }

            File base = new File(target.getParentFile(), base_prefix
                    + target.getName()); // file����Prefix: base_������

            if (base.exists()) {
                File result = ACCreator.create(target, base, base_prefix);
                return new FilePath(result);
            }
            return null;
        }
    }

    private static class EVCreatorExecutor implements FileCallable<FilePath[]> {
        private final String fileName;

        public EVCreatorExecutor(String fileName) {
            this.fileName = fileName;
        }

        public FilePath[] invoke(File f, VirtualChannel channel)
                throws IOException, InterruptedException {
            File target = new File(f, fileName);
            try {
                FilePath[] results = executes(target, PREFIX_ARRAY);
                return results;
                // return execute(target, base_prefix);
            } catch (ProjectException e) {
                throw new IOException(e);
            }
        }

        private FilePath[] executes(File target, String[] prefixArray)
                throws ProjectException {
            List<FilePath> returnList = new ArrayList<FilePath>();
            for (String base_prefix : prefixArray) {
                FilePath result = execute(target, base_prefix);
                returnList.add(result);
            }
            return returnList.toArray(new FilePath[returnList.size()]);
        }

        private FilePath execute(File target, String base_prefix)
                throws ProjectException {

            File jsonFile = new File(target.getParentFile(), target.getName()
                    + ".json");
            File jsonFile_base = new File(target.getParentFile(), base_prefix
                    + target.getName() + ".json");
            if (jsonFile_base.exists()) {
                File result = EVCreator.createFromJSON(jsonFile, jsonFile_base,
                        base_prefix);
                return new FilePath(result);
            }

            File base = new File(target.getParentFile(), base_prefix
                    + target.getName()); // file����Prefix: base_������
            if (base.exists()) {
                File result = EVCreator.create(target, base, base_prefix);
                return new FilePath(result);
            }
            return null;
        }
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * Descriptor for {@link EVMToolsBuilder}. Used as a singleton. The class is
     * marked as public so that it can be accessed from views.
     * 
     * <p>
     * See
     * <tt>src/main/resources/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension
    // This indicates to Jenkins that this is an implementation of an extension
    // point.
    public static final class DescriptorImpl extends
            BuildStepDescriptor<Builder> {
        private String prefixs;

        private String addresses;

        // /**
        // * To persist global configuration information, simply store it in a
        // * field and call save().
        // *
        // * <p>
        // * If you don't want fields to be persisted, use <tt>transient</tt>.
        // */
        // private boolean useFrench;

        /**
         * In order to load the persisted global configuration, you have to call
         * load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         * 
         * @param value
         *            This parameter receives the value that the user has typed.
         * @return Indicates the outcome of the validation. This is sent to the
         *         browser.
         *         <p>
         *         Note that returning {@link FormValidation#error(String)} does
         *         not prevent the form from being saved. It just means that a
         *         message will be displayed to the user.
         */
        public FormValidation doCheckName(@QueryParameter
        String value) throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a Project File Name");
            if (value.length() < 4)
                return FormValidation.warning("Isn't the name too short?");
            return FormValidation.ok();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project
            // types
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "EVM�W�v�c�[��";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData)
                throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            // useFrench = formData.getBoolean("useFrench");
            // ^Can also use req.bindJSON(this, formData);
            // (easier when there are many fields; need set* methods for this,
            // like setUseFrench)

            prefixs = formData.getString("prefixs");
            if (formData.has("useMail")) {
                JSONObject useMail = formData.getJSONObject("useMail");
                addresses = useMail.getString("addresses");
            } else {
                addresses = null;
            }
            save();
            return super.configure(req, formData);
        }

        // /**
        // * This method returns true if the global configuration says we should
        // * speak French.
        // *
        // * The method name is bit awkward because global.jelly calls this
        // method
        // * to determine the initial state of the checkbox by the naming
        // * convention.
        // */
        // public boolean getUseFrench() {
        // return useFrench;
        // }

        // Getter������΁A�ۑ������ۂ��B
        public String getPrefixs() {
            return prefixs;
        }

        // Getter������΁A�ۑ������ۂ��B
        public String getAddresses() {
            return addresses;
        }
    }

}
