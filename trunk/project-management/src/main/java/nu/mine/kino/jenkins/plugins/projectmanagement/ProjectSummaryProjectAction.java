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
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import nu.mine.kino.entity.EVMViewBean;
import nu.mine.kino.jenkins.plugins.projectmanagement.utils.PMUtils;
import nu.mine.kino.projects.utils.ProjectUtils;
import nu.mine.kino.projects.utils.ReadUtils;

public class ProjectSummaryProjectAction implements Action {

    private static final String seriesFileNameSuffix = PMConstants.SERIES_DAT_FILENAME;

    private final AbstractProject<?, ?> project;

    private FORMAT format;

    public ProjectSummaryProjectAction(AbstractProject<?, ?> project) {
        this.project = project;
    }

    public AbstractProject<?, ?> getProject() {
        return project;
    }

    public String getIconFileName() {
        return "/plugin/project-management/images/24x24/user_suit.png";
        // return "graph.gif";
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

    public ProjectSummaryAction[] getGraphSeriesActions() throws IOException {
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

    public ProjectSummaryAction[] getGraphSeriesActionsWithPrefix(String prefix)
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

        // Date startDate = projectOrg.getProjectStartDate();
        // Date endDate = projectOrg.getProjectEndDate();
        // Date cursor = startDate;
        // while (cursor.getTime() <= endDate.getTime()) {
        // System.out.println(cursor);
        // System.out.println(ProjectUtils.calculateTotalPVOfProject(
        // projectOrg, cursor));
        // cursor = DateUtils.addDays(cursor, 1);
        //
        // }

        // sort
        Collections.sort(actions, new Comparator<ProjectSummaryAction>() {
            /**
             * ���t���ߋ� �� �����ɂȂ�悤�Ƀ\�[�g�B�B
             * 
             * @param arg0
             * @param arg1
             * @return
             */
            @Override
            public int compare(ProjectSummaryAction arg0,
                    ProjectSummaryAction arg1) {
                Date baseDate0 = arg0.getBaseDate();
                Date baseDate1 = arg1.getBaseDate();
                return baseDate0.compareTo(baseDate1);
            }
        });
        return actions.toArray(new ProjectSummaryAction[actions.size()]);
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