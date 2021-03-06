package com.niafikra.meddela.ui.vaadin.dashboard.settings.notifications.trigger

import com.niafikra.meddela.data.Trigger
import com.vaadin.data.Property
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import java.text.DateFormatSymbols

/**
 * A form for setting up cron task schedule
 * Author: Boniface Chacha <bonifacechacha@gmail.com>
 * Date: 7/20/12
 * Time: 10:28 PM
 */
class ScheduleForm extends VerticalLayout implements Property.ValueChangeListener {

    HorizontalLayout footer

    HashMap<String, StringBuffer> fields = new HashMap<String, StringBuffer>()
    /**
     * Selectors for the field in the scheduling string
     */
    ScheduleField month, day, dayOfWeek, hour, minute

    TextField scheduleStringField
    Trigger trigger
    /**
     * Create the form
     */
    ScheduleForm(Trigger trigger,boolean isNew) {
        this.trigger = trigger
        scheduleStringField = new TextField("CRON Schedule String")
        footer = new HorizontalLayout()
        month = new ScheduleField("Month", fields)
        day = new ScheduleField("Day", fields)
        dayOfWeek = new ScheduleField("Day Of Week", fields)
        hour = new ScheduleField("Hour", fields)
        minute = new ScheduleField("Minute", fields)
        build()
        if(!isNew)load()
    }

    def build() {
        setMargin(true)

        HorizontalLayout fieldsLayout = new HorizontalLayout()
        scheduleStringField.setWidth("100%")

        month.addListener(this)
        month.addItem("every")
        new DateFormatSymbols().shortMonths.each {month.addItem(it)}
        fieldsLayout.addComponent(month)

        day.addListener(this)
        day.addItem("every")
        day.addItem("last")
        (1..31).each {day.addItem(it.toString())}
        fieldsLayout.addComponent(day)


        dayOfWeek.addListener(this)
        dayOfWeek.addItem("every")
        new DateFormatSymbols().shortWeekdays.each {dayOfWeek.addItem(it)}
        fieldsLayout.addComponent(dayOfWeek)

        hour.addListener(this)
        hour.addItem("every")
        (0..23).each {hour.addItem(it.toString()) }
        fieldsLayout.addComponent(hour)

        minute.addListener(this)
        minute.addItem("every")
        (0..59).each {minute.addItem(it.toString())}
        fieldsLayout.addComponent(minute)
        fieldsLayout.setSpacing(true)
        fieldsLayout.setMargin(true)

        addComponent(fieldsLayout)
        addComponent(scheduleStringField)

    }

    def load(){
        scheduleStringField.setValue(trigger.getSchedule())
        String[] shedule =trigger.getSchedule().split("[ ]")
        minute.setValue(shedule[0])
        hour.setValue(shedule[1])
        day.setValue(shedule[2])
        month.setValue(shedule[3])
        dayOfWeek.setValue(shedule[4])
    }

    def commit(){
        trigger.schedule=scheduleStringField .getValue()
    }

    @Override
    void valueChange(Property.ValueChangeEvent event) {
        scheduleStringField.setValue(
                fields.get("Minute") + " " + fields.get("Hour") + " " + fields.get("Day") + " " + fields.get("Month") + " " + fields.get("Day Of Week"))
    }
}
