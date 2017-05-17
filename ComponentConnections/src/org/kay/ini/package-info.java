
@TemplateRegistration(
        folder = "Other",
        iconBase="org/kay/ini/iconbase.png", 
        displayName = "#HTMLtemplate_displayName", 
        content = "deployIniTemplate.ini",
        description = "description.html",
        scriptEngine="freemarker")
@Messages(value = "HTMLtemplate_displayName=deploy ini File")


package org.kay.ini;


import org.netbeans.api.templates.TemplateRegistration;
import org.openide.util.NbBundle.Messages;