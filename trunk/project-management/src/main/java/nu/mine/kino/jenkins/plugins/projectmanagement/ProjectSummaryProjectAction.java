package nu.mine.kino.jenkins.plugins.projectmanagement;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nu.mine.kino.entity.EVMViewBean;
import nu.mine.kino.entity.Project;
import nu.mine.kino.jenkins.plugins.projectmanagement.utils.PMUtils;
import nu.mine.kino.projects.ProjectException;
import nu.mine.kino.projects.utils.ProjectUtils;
import nu.mine.kino.projects.utils.ReadUtils;

/**
 * �v���W�F�N�g�̃g�b�v�y�[�W�ɕ\������ׂ��f�[�^���W�v����A�N�V�����B ���܂�r���h�Ɉˑ����Ȃ����n��̏��𐮗����ĕ\������B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ProjectSummaryProjectAction implements Action {

    private static final String seriesFileNameSuffix = PMConstants.SERIES_DAT_FILENAME;

    private final AbstractProject<?, ?> project;

    private FORMAT format;// ���X�g�\�����A�O���t�\�����̃t���O

    public ProjectSummaryProjectAction(AbstractProject<?, ?> project) {
        this.project = project;
    }

    public AbstractProject<?, ?> getProject() {
        return project;
    }

    public String getIconFileName() {
        String ret = null;
        switch (format) {
        case LIST:
            ret = "/plugin/project-management/images/24x24/application_view_detail.png";
            break;
        case GRAPH:
            ret = "/plugin/project-management/images/24x24/chart_line.png";
            break;
        default:
            break;
        }
        return ret;
    }

    public String getDisplayName() {
        return String.format("�v���W�F�N�g�T�}���[(%s)", format.getName());
    }

    public String getUrlName() {
        return "project-summary" + "-" + format.getUrlSuffix();
    }

    public void setFormat(FORMAT format) {
        this.format = format;
    }

    public ProjectSummaryAction[] getSeriesActions() throws IOException {
        // List<ProjectSummaryAction> actions = new
        // ArrayList<ProjectSummaryAction>();
        // String file = PMConstants.BASE + "_" + seriesFileNameSuffix;
        // AbstractBuild<?, ?> build = PMUtils.findBuild(project, file);
        // if (build == null) {
        // return new ProjectSummaryAction[0];
        // }
        // String data = ReadUtils.readFile(new File(build.getRootDir(), file));
        // BufferedReader reader = new BufferedReader(new StringReader(data));
        // String line;
        // while ((line = reader.readLine()) != null) {
        // String[] split = line.split("\t");
        // String buildNumber = split[1];
        // AbstractBuild<?, ?> record = project.getBuildByNumber(Integer
        // .parseInt(buildNumber));
        // actions.add(record.getAction(ProjectSummaryAction.class));
        // }
        // return actions.toArray(new ProjectSummaryAction[actions.size()]);
        return this.getSeriesActionsWithPrefix(PMConstants.BASE);
    }

    public EVMViewBean[] getEVMViewBeans() throws IOException {
        return this.getGraphSeriesActionsWithPrefix(PMConstants.BASE);
    }

    public ProjectSummaryAction[] getSeriesActionsBase() throws IOException {
        return getSeriesActions();
    }

    public ProjectSummaryAction[] getSeriesActionsBase1() throws IOException {
        return this.getSeriesActionsWithPrefix(PMConstants.BASE + "1");
    }

    public ProjectSummaryAction[] getSeriesActionsBase2() throws IOException {
        return this.getSeriesActionsWithPrefix(PMConstants.BASE + "2");
    }

    public ProjectSummaryAction[] getSeriesActionsWithPrefix(String prefix)
            throws IOException {
        List<ProjectSummaryAction> actions = new ArrayList<ProjectSummaryAction>();
        String file = prefix + "_" + seriesFileNameSuffix;
        AbstractBuild<?, ?> build = PMUtils.findBuild(project, file);
        if (build == null) {
            return new ProjectSummaryAction[0];
        }
        String data = ReadUtils.readFile(new File(build.getRootDir(), file));
        BufferedReader reader = new BufferedReader(new StringReader(data));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\t");
            String buildNumber = split[1];
            AbstractBuild<?, ?> record = project.getBuildByNumber(Integer
                    .parseInt(buildNumber));

            ProjectSummaryAction a = PMUtils.findActionByUrlEndsWith(record,
                    ProjectSummaryAction.class, PMConstants.BASE);
            if (a != null) {
                actions.add(a);
            }
        }
        return actions.toArray(new ProjectSummaryAction[actions.size()]);
    }

    public EVMViewBean[] getGraphSeriesActionsWithPrefix(String prefix)
            throws IOException {
        Map<Date, EVMViewBean> actionsMap = new HashMap<Date, EVMViewBean>();
        String file = prefix + "_" + seriesFileNameSuffix;
        AbstractBuild<?, ?> build = PMUtils.findBuild(project, file);
        if (build == null) {
            return new EVMViewBean[0];
        }

        // �܂��́A���f�[�^���擾���āA���X�g(Map)�փZ�b�g�B
        String data = ReadUtils.readFile(new File(build.getRootDir(), file));
        BufferedReader reader = new BufferedReader(new StringReader(data));
        Double bac = Double.NaN;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\t");
            String buildNumber = split[1];
            AbstractBuild<?, ?> record = project.getBuildByNumber(Integer
                    .parseInt(buildNumber));

            ProjectSummaryAction a = PMUtils.findActionByUrlEndsWith(record,
                    ProjectSummaryAction.class, PMConstants.BASE);
            if (a != null) {
                EVMViewBean currentPVACEV = a.getCurrentPVACEV();
                actionsMap.put(currentPVACEV.getBaseDate(), currentPVACEV);

                if (Double.isNaN(bac)) {
                    bac = currentPVACEV.getBac();
                }

            }
        }
        // �܂��́A���f�[�^���擾���āA���X�g(Map)�փZ�b�g�B �ȏ�

        // �Â��āAPV��BAC�Ȃǂ̌v�Z�l���A���X�g(Map)�փZ�b�g�B
        Map<Date, Double> pvMap = calculateTotalPVOfProject(project,
                PMConstants.BASE);
        Set<Date> keySet = pvMap.keySet();
        for (Date date : keySet) {
            EVMViewBean evmViewBean = createEVMViewBean(pvMap.get(date), bac,
                    date);
            // ���łɂ������̃f�[�^�͏㏑�����Ȃ��B
            if (!actionsMap.containsKey(evmViewBean.getBaseDate())) {
                // ���A�x���Ńf�[�^�Ȃ��̏ꍇ���ǉ����Ȃ��B
                if (ProjectUtils.isHoliday(
                        getProject(project, PMConstants.BASE),
                        evmViewBean.getBaseDate())) {
                    actionsMap.put(evmViewBean.getBaseDate(), evmViewBean);
                }
            }
        }

        List<EVMViewBean> actions = new ArrayList<EVMViewBean>(
                actionsMap.values());
        // sort
        Collections.sort(actions, new Comparator<EVMViewBean>() {
            /**
             * ���t���ߋ� �� �����ɂȂ�悤�Ƀ\�[�g�B�B
             * 
             * @param arg0
             * @param arg1
             * @return
             */
            @Override
            public int compare(EVMViewBean arg0, EVMViewBean arg1) {
                Date baseDate0 = arg0.getBaseDate();
                Date baseDate1 = arg1.getBaseDate();
                return baseDate0.compareTo(baseDate1);
            }
        });
        return actions.toArray(new EVMViewBean[actions.size()]);
    }

    private EVMViewBean createEVMViewBean(Double pv, Double bac, Date date) {
        EVMViewBean evmViewBean = new EVMViewBean();
        evmViewBean.setBaseDate(date);
        evmViewBean.setPlannedValue(pv);
        evmViewBean.setBac(bac);
        evmViewBean.setActualCost(Double.NaN);
        evmViewBean.setEarnedValue(Double.NaN);
        evmViewBean.setCpi(Double.NaN);
        evmViewBean.setSpi(Double.NaN);
        return evmViewBean;
    }

    private Map<Date, Double> calculateTotalPVOfProject(
            AbstractProject<?, ?> jenkinsProject, String suffix)
            throws IOException {
        Project targetProject = getProject(jenkinsProject, suffix);
        Map<Date, Double> pvMap = ProjectUtils
                .calculateTotalPVOfProject(targetProject);
        return pvMap;
    }

    private Project getProject(AbstractProject<?, ?> jenkinsProject,
            String suffix) throws IOException {
        AbstractBuild<?, ?> record = jenkinsProject
                .getBuildByNumber(getBuildNumber());
        ProjectSummaryAction a = PMUtils.findActionByUrlEndsWith(record,
                ProjectSummaryAction.class, suffix);
        String fileName = a.getFileName();
        try {
            Project targetProject = a.getProject(fileName);
            return targetProject;
        } catch (ProjectException e) {
            throw new IOException(e);
        }
    }

    public EVMViewBean getCurrentPVACEV() {
        final AbstractBuild<?, ?> tb = project.getLastSuccessfulBuild();
        AbstractBuild<?, ?> b = project.getLastBuild();
        while (b != null) {
            ProjectSummaryAction a = PMUtils.findActionByUrlEndsWith(b,
                    ProjectSummaryAction.class, PMConstants.BASE);
            if (a != null)
                return a.getCurrentPVACEV();
            if (b == tb)
                // if even the last successful build didn't produce the test
                // result,
                // that means we just don't have any tests configured.
                return null;
            b = b.getPreviousBuild();
        }
        return null;
    }

    public Date getBaseDate() {
        final AbstractBuild<?, ?> tb = project.getLastSuccessfulBuild();
        AbstractBuild<?, ?> b = project.getLastBuild();
        while (b != null) {
            ProjectSummaryAction a = null;
            List<ProjectSummaryAction> actions = b
                    .getActions(ProjectSummaryAction.class);
            for (ProjectSummaryAction tmpA : actions) {
                if (tmpA.getUrlName().endsWith(PMConstants.BASE)) {
                    a = tmpA;
                }
            }
            if (a != null)
                return a.getBaseDate();
            if (b == tb)
                // if even the last successful build didn't produce the test
                // result,
                // that means we just don't have any tests configured.
                return null;
            b = b.getPreviousBuild();
        }
        return null;
    }

    public int getBuildNumber() {
        ProjectSummaryAction action = PMUtils.getMostRecentSummaryAction(
                project, PMConstants.BASE);
        if (action != null) {
            return action.getBuildNumber();
        }
        return 0;
    }

    // /**
    // * �����j���[�̃����N�𖳗�����L�̉�ʂ֑J�ڂ����Ă���B�{����index.jelly�ɍs���B
    // *
    // * @param request
    // * @param response
    // * @throws IOException
    // */
    // public void doIndex(final StaplerRequest request,
    // final StaplerResponse response) throws IOException {
    // AbstractBuild<?, ?> build = project.getLastSuccessfulBuild();
    //
    // String path = String.format("/job/%s/%d/%s", build.getProject()
    // .getName(), build.getNumber(), "project-summary");
    // // System.out.println(path);
    // // System.out.println(request.getContextPath());
    // response.sendRedirect2(request.getContextPath() + path);
    // }
}
