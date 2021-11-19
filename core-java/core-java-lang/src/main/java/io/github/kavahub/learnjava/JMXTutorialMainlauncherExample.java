package io.github.kavahub.learnjava;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JMXTutorialMainlauncherExample {
    public static void main(String[] args) {

        log.debug("This is basic JMX tutorial");

        try {
            ObjectName objectName = new ObjectName("io.github.kavahub.learnjava:type=basic,name=game");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new Game(), objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }

        log.debug("Registration for Game mbean with the platform server is successfull");
        log.debug("Please open jconsole to access Game mbean");
        log.debug("PID: {}", ProcessHandle.current().pid());

        while (true) {
            // to ensure application does not terminate
        }
    }   
    
    @Slf4j
    public static class Game implements GameMBean {

        private String playerName;
    
        @Override
        public void playFootball(String clubName) {
            log.debug(this.playerName + " playing football for " + clubName);
        }
    
        @Override
        public String getPlayerName() {
            log.debug("Return playerName " + this.playerName);
            return playerName;
        }
    
        @Override
        public void setPlayerName(String playerName) {
            log.debug("Set playerName to value " + playerName);
            this.playerName = playerName;
        }
    
    }

    public static interface GameMBean {

        public void playFootball(String clubName);
    
        public String getPlayerName();
    
        public void setPlayerName(String playerName);
    
    }
}
