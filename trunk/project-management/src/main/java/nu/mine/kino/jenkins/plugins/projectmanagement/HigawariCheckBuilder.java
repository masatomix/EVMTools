package nu.mine.kino.jenkins.plugins.projectmanagement;

import hudson.AbortException;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;
import nu.mine.kino.entity.Project;
import nu.mine.kino.jenkins.plugins.projectmanagement.utils.PMUtils;
import nu.mine.kino.projects.JSONProjectCreator;
import nu.mine.kino.projects.ProjectException;
import nu.mine.kino.projects.utils.ProjectUtils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * {@link EVMToolsBuilder} がセットされたジョブを探し、現在の日替わり基準日を探してくる。
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 * 
 * @author Masatomi KINO.
 */
public class HigawariCheckBuilder extends Builder {

    private String targetProjects;

    // ネストしたテキストボックスを作成するときの定石。
    public static class EnableTextBlock {
        private String targetProjects;

        @DataBoundConstructor
        public EnableTextBlock(String targetProjects) {
            this.targetProjects = targetProjects;
        }
    }

    private final EnableTextBlock useFilter;

    // Fields in config.jelly must match the parameter names in the
    // "DataBoundConstructor"
    @DataBoundConstructor
    public HigawariCheckBuilder(EnableTextBlock useFilter) {
        this.useFilter = useFilter;
        if (useFilter != null) { // targetProjectsは、ココを通らなければ初期値に戻る。
            this.targetProjects = useFilter.targetProjects;
        }
    }

    public String getTargetProjects() {
        return targetProjects;
    }

    public String getSamples() {
        return getDescriptor().defaultSamples();
    }

    /**
     */
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher,
            BuildListener listener) throws InterruptedException, IOException {
        List<AbstractProject<?, ?>> projects = null;
        if (useFilter == null) {
            projects = PMUtils.findProjectsWithEVMToolsBuilder();
        } else {
            String[] targetProjectsArray = targetProjects.split("\n");
            projects = PMUtils
                    .findProjectsWithEVMToolsBuilder(targetProjectsArray);
        }

        StringBuffer buf = new StringBuffer();
        for (AbstractProject<?, ?> project : projects) {
            File newBaseDateFile = PMUtils.findBaseDateFile(project); // buildDirの新しいファイル
            if (newBaseDateFile != null) {
                Date baseDateFromBaseDateFile = PMUtils
                        .getBaseDateFromBaseDateFile(newBaseDateFile);
                String dateStr = DateFormatUtils.format(
                        baseDateFromBaseDateFile, "yyyyMMdd");

                String msg = String
                        .format("%s\t%s", project.getName(), dateStr);
                buf.append(msg);
                if (checkNextTradingDate(listener, project,
                        PMUtils.findProjectFileName(project))) {// 過去ならば
                    buf.append("\t日替わりチェックエラー");
                }
            } else {
                String msg = String.format("%s\t日替処理が未実施か、"
                        + "ワークスペースに存在する旧バージョンの日替ファイルしか存在しない。"
                        + "日替処理を実施後、ファイルが見つかるようになります。", project.getName());
                buf.append(msg);
            }
            buf.append("\n");
        }
        listener.getLogger().println(new String(buf));

        return true;
    }

    /**
     * EVMスケジュールファイルの基準日の次営業日が、今日の日付より過去であるかをtrue/falseで返す
     * 過去の場合true。同日か未来の場合false
     * 
     * @param listener
     * @param jenkinsProject
     * @param builder
     * @return
     * @throws IOException
     * @throws AbortException
     */
    private boolean checkNextTradingDate(BuildListener listener,
            AbstractProject<?, ?> jenkinsProject, String evmFileName)
            throws IOException, AbortException {

        String evmJSONFileName = ProjectUtils.findJSONFileName(evmFileName);
        AbstractBuild<?, ?> newestBuild = PMUtils
                .findNewestBuild(jenkinsProject);
        File newestJsonFile = new File(newestBuild.getRootDir(),
                evmJSONFileName + "." + PMConstants.TMP_EXT);
        System.out.println(newestJsonFile.getAbsolutePath());
        try {
            Project evmProject = new JSONProjectCreator(newestJsonFile)
                    .createProject();
            Date nextTradingDate = ProjectUtils.nextTradingDate(evmProject);
            Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            System.out.println(DateFormatUtils.format(nextTradingDate,
                    "yyyyMMdd"));
            System.out.println(DateFormatUtils.format(now, "yyyyMMdd"));
            boolean before = nextTradingDate.before(now);
            System.out.println(before);
            return before;
        } catch (ProjectException e) {
            listener.getLogger().println(e);
            throw new AbortException(e.getMessage());
        }
        //
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * Descriptor for {@link HigawariCheckBuilder}. Used as a singleton. The
     * class is marked as public so that it can be accessed from views.
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
            return "日替わりチェックツール";
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

            save();
            return super.configure(req, formData);
        }

        // https://wiki.jenkins-ci.org/display/JENKINS/Basic+guide+to+Jelly+usage+in+Jenkins
        // config.jellyから呼び出される、デフォルト値をセットするメソッド。
        public String defaultSamples() {
            StringBuffer buf = new StringBuffer();
            List<AbstractProject<?, ?>> projects = PMUtils
                    .findProjectsWithEVMToolsBuilder();
            for (int i = 0; i < projects.size(); i++) {
                buf.append(projects.get(i).getName());
                if (i < projects.size() - 1) {
                    buf.append("\n");
                }
            }
            return new String(buf);
        }
    }

}
