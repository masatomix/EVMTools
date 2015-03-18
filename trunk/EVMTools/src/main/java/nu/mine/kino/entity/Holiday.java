/******************************************************************************
 * Copyright (c) 2008-2014 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 ******************************************************************************/

package nu.mine.kino.entity;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 */
public class Holiday extends BaseHoliday {
    @Override
    public void setDate(Date date) {
        super.setDate(date);
        setDayOfWeek(this.getDayOfWeek(date));
    }

    private String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            return "��";
        case Calendar.MONDAY:
            return "��";
        case Calendar.TUESDAY:
            return "��";
        case Calendar.WEDNESDAY:
            return "��";
        case Calendar.THURSDAY:
            return "��";
        case Calendar.FRIDAY:
            return "��";
        case Calendar.SATURDAY:
            return "�y";
        }
        return null;
    }
}