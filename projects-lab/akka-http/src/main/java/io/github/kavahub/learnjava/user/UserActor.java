package io.github.kavahub.learnjava.user;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.FI;
import io.github.kavahub.learnjava.user.UserMessages.ActionPerformed;
import io.github.kavahub.learnjava.user.UserMessages.CreateUserMessage;
import io.github.kavahub.learnjava.user.UserMessages.GetUserMessage;

/**
 * Actor
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
public class UserActor extends AbstractActor {

    private UserService userService = new UserService();
  
    static Props props() {
      return Props.create(UserActor.class);
    }
  
    @Override
    public Receive createReceive() {
      return receiveBuilder()
              .match(CreateUserMessage.class, handleCreateUser())
              .match(GetUserMessage.class, handleGetUser())
              .build();
    }
  
    private FI.UnitApply<CreateUserMessage> handleCreateUser() {
      return createUserMessageMessage -> {
        userService.createUser(createUserMessageMessage.getUser());
        sender().tell(new ActionPerformed(String.format("User %s created.", createUserMessageMessage.getUser()
                .getName())), getSelf());
      };
    }
  
    private FI.UnitApply<GetUserMessage> handleGetUser() {
      return getUserMessageMessage -> {
        sender().tell(userService.getUser(getUserMessageMessage.getUserId()), getSelf());
      };
    }
    
}
