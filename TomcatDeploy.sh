gradle clean war
cd /usr/local/Tomcat/bin
sh shutdown.sh
mv ~/Documents/SimRNAWebApp/Controller/build/libs/molecularrendering.war /usr/local/Tomcat/webapps/
cd /usr/local/Tomcat/bin
sh startup.sh
