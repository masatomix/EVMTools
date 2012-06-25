/******************************************************************************
 * Copyright (c) 2012 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//作成日: 2012/06/24

package nu.mine.kino.plugin.webrecorder.views;

import nu.mine.kino.plugin.webrecorder.jobs.DriverJob;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class DriverView extends ViewPart {

    public static final String ID = "nu.mine.kino.plugin.webrecorder.views.DriverView"; //$NON-NLS-1$

    private Text txtURL;

    private Text textRequestBody;

    private Text textResult;

    public DriverView() {
    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(3, false));

        final Combo comboMethod = new Combo(container, SWT.READ_ONLY);
        comboMethod.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false, 1, 1));
        comboMethod.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if ("POST".equals(comboMethod.getText())) {
                    textRequestBody.setEditable(true);
                } else {
                    textRequestBody.setEditable(false);
                    // 色くらい変えるか
                }
            }
        });
        comboMethod.setItems(new String[] { "GET", "POST" });
        comboMethod.select(1);

        txtURL = new Text(container, SWT.BORDER);
        txtURL.setText("http://www.masatom.in/pukiwiki/?cmd=search");
        txtURL.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
                1));

        Button requestButton = new Button(container, SWT.NONE);
        requestButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String method = comboMethod.getText();
                String url = txtURL.getText();
                String body = textRequestBody.getText();

                Job job = new DriverJob("Driver", method, url, body, textResult);
                // ダイアログを出す
                job.setUser(true);
                job.schedule();

            }

        });
        requestButton.setText("\u9001\u4FE1");

        Label lblNewLabel = new Label(container, SWT.NONE);
        lblNewLabel.setText("Request Body:");
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);

        SashForm sashForm = new SashForm(container, SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3,
                1));

        textRequestBody = new Text(sashForm, SWT.BORDER | SWT.READ_ONLY
                | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
        textRequestBody.setText("encode_hint=%A4%D7&word=TESTTEST&type=AND");

        textResult = new Text(sashForm, SWT.BORDER | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
        sashForm.setWeights(new int[] { 1, 1 });

        createActions();
        initializeToolBar();
        initializeMenu();
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        // Create the actions
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars()
                .getToolBarManager();
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        IMenuManager menuManager = getViewSite().getActionBars()
                .getMenuManager();
    }

    @Override
    public void setFocus() {
        // Set the focus
    }
}
