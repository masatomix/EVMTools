package nu.mine.kino.jenkins.plugins.projectmanagement;

import hudson.AbortException;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Hudson;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import jenkins.model.Jenkins;

import net.sf.json.JSONObject;
import nu.mine.kino.entity.Project;
import nu.mine.kino.jenkins.plugins.projectmanagement.EVMToolsBuilder.DescriptorImpl;
import nu.mine.kino.jenkins.plugins.projectmanagement.utils.PMUtils;
import nu.mine.kino.projects.JSONProjectCreator;
import nu.mine.kino.projects.ProjectException;
import nu.mine.kino.projects.utils.ProjectUtils;
import nu.mine.kino.projects.utils.Utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.StopWatch;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * {@link EVMToolsBuilder} ���Z�b�g���ꂽ�W���u��T���A���݂̓��ւ������T���Ă���B
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 * 
 * @author Masatomi KINO.
 */
public class HigawariCheckBuilder extends Builder {

    // �l�X�g�����e�L�X�g�{�b�N�X���쐬����Ƃ��̒�΁B
    private String targetProjects;

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
    public HigawariCheckBuilder(EnableTextBlock useFilter,
            EnableUseMailTextBlock useMail) {
        this.useFilter = useFilter;
        this.useMail = useMail;
        if (useFilter != null) { // targetProjects�́A�R�R��ʂ�Ȃ���Ώ����l�ɖ߂�B
            this.targetProjects = useFilter.targetProjects;
        }
        if (useMail != null) { // targetProjects�́A�R�R��ʂ�Ȃ���Ώ����l�ɖ߂�B
            this.addresses = useMail.addresses;
        }
    }

    public String getTargetProjects() {
        return targetProjects;
    }

    // �l�X�g�����e�L�X�g�{�b�N�X���쐬����Ƃ��̒�΁B

    // �l�X�g�����e�L�X�g�{�b�N�X���쐬����Ƃ��̒�΁B
    private String addresses;

    public static class EnableUseMailTextBlock {
        private String addresses;

        @DataBoundConstructor
        public EnableUseMailTextBlock(String addresses) {
            this.addresses = addresses;
        }
    }

    private final EnableUseMailTextBlock useMail;

    public String getAddresses() {
        return addresses;
    }

    // �l�X�g�����e�L�X�g�{�b�N�X���쐬����Ƃ��̒�΁B

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
            File newBaseDateFile = PMUtils.findBaseDateFile(project); // buildDir�̐V�����t�@�C��
            if (newBaseDateFile != null) {
                Date baseDateFromBaseDateFile = PMUtils
                        .getBaseDateFromBaseDateFile(newBaseDateFile);
                String dateStr = DateFormatUtils.format(
                        baseDateFromBaseDateFile, "yyyyMMdd");

                String msg = String
                        .format("%s\t%s", project.getName(), dateStr);
                buf.append(msg);
                if (checkNextTradingDate(listener, project,
                        PMUtils.findProjectFileName(project))) {// �ߋ��Ȃ��
                    buf.append("\t���ւ��`�F�b�N�G���[");
                }
            } else {
                String msg = String.format("%s\t���֏����������{���A"
                        + "���[�N�X�y�[�X�ɑ��݂��鋌�o�[�W�����̓��փt�@�C���������݂��Ȃ��B"
                        + "���֏��������{��A�t�@�C����������悤�ɂȂ�܂��B", project.getName());
                buf.append(msg);
            }
            buf.append("\n");
        }
        listener.getLogger().println(new String(buf));

        String BUILD_URL = new StringBuilder()
                .append(Jenkins.getInstance().getRootUrl())
                .append(build.getUrl()).toString();
        String PROJECT_NAME = build.getProject().getName();
        String BUILD_NUMBER = String.valueOf(build.getNumber());
        String subject = String.format("%s ����̃��[��(#%s)", PROJECT_NAME,
                BUILD_NUMBER);
        String footer = String.format(
                "Check console output at %s to view the results.", BUILD_URL);
        String addresses = this.addresses;

        buf.append("\n");
        buf.append("\n");
        buf.append(footer);

        StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("�ȉ��A" + PROJECT_NAME + " ����̃��[���ł��B\n\n");
        msgBuf.append(buf);
        String message = new String(msgBuf);

        System.out.printf("[EVM Tools] ����: %s\n", addresses);
        System.out.printf("[EVM Tools] �T�u�W�F�N�g: %s\n", subject);
        System.out.printf("[EVM Tools] �{��:\n%s\n", new String(buf));

        if (useMail != null) {
            StopWatch watch = new StopWatch();
            watch.start();
            listener.getLogger().println("[EVM Tools] ����: " + addresses);
            listener.getLogger().println("[EVM Tools] �T�u�W�F�N�g: " + subject);

            if (!StringUtils.isEmpty(addresses)) {
                String[] addressesArray = Utils.parseCommna(addresses);
                for (String to : addressesArray) {
                    System.out.printf("����: [%s]\n", to);
                }
                try {
                    if (addressesArray.length > 0) {
                        PMUtils.sendMail(addressesArray, subject, message);
                    } else {
                        String errorMsg = "���[�����M�Ɏ��s���܂����B����̐ݒ肪����Ă��܂���";
                        listener.getLogger().println("[EVM Tools] " + errorMsg);
                        throw new AbortException(errorMsg);
                    }
                } catch (MessagingException e) {
                    String errorMsg = "���[�����M�Ɏ��s���܂����B�u�V�X�e���̐ݒ�v�� E-mail �ʒm �̐ݒ�∶��Ȃǂ��������Ă�������";
                    listener.getLogger().println("[EVM Tools] " + errorMsg);
                    throw new AbortException(errorMsg);
                }
            }
            watch.stop();
            System.out.printf("���[�����M����:[%d] ms\n", watch.getTime());
            watch.reset();
            watch = null;
        }
        return true;
    }

    /**
     * EVM�X�P�W���[���t�@�C���̊���̎��c�Ɠ����A�����̓��t���ߋ��ł��邩��true/false�ŕԂ�
     * �ߋ��̏ꍇtrue�B�����������̏ꍇfalse
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
            return "���ւ��`�F�b�N�c�[��";
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
        // config.jelly����Ăяo�����A�f�t�H���g�l���Z�b�g���郁�\�b�h�B
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
