package io.github.kavahub.learnjava;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Service("playerService")
public class PlayerServiceImpl implements PlayerService {
    private final SessionFactory sessionFactory;

    public PlayerServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Response create(PlayerType playerType) {

        // get player information from formal arguments
        Player playerForCreate = new Player();
        playerForCreate.setName(playerType.getName());
        playerForCreate.setAge(playerType.getAge());
        playerForCreate.setMatches(playerType.getMatches());

        // inserts into database & return playerId (primary_key)
        int playerId = (Integer) sessionFactory.getCurrentSession().save(playerForCreate);
        return Response.status(Status.CREATED).entity(playerId).build();
    }

    /**
     * retrieves a player object based on the playerId supplied in the formal
     * argument using @PathParam
     */
    @Transactional
    @Override
    public Response getById(int id) {

        Player player = this.findById(id);
        if (player == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // set values and return
        PlayerType getplayer = new PlayerType();
        getplayer.setPlayerId(player.getPlayerId());
        getplayer.setName(player.getName());
        getplayer.setAge(player.getAge());
        getplayer.setMatches(player.getMatches());
        return Response.ok(getplayer).build();
    }

    /**
     * 
     */
    @Transactional
    @Override
    public Response update(int id, PlayerType playerType) {

        Player playerForUpdate = this.findById(id);
        if (playerForUpdate == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        playerForUpdate.setName(playerType.getName());
        playerForUpdate.setAge(playerType.getAge());
        playerForUpdate.setMatches(playerType.getMatches());

        // update database with player information and return success msg
        sessionFactory.getCurrentSession().update(playerForUpdate);
        return Response.status(Status.NO_CONTENT).build();
    }


    @Transactional
    @Override
    public Response delete(int id) {
        Player playerForDelete = this.findById(id);
        if (playerForDelete == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // delete player information and return success msg
        sessionFactory.getCurrentSession().delete(playerForDelete);
        return Response.status(Status.NO_CONTENT).build();
    }

    /**
     * retrieves all players stored
     */
    @Transactional
    @Override
    public Response getByName(String name) {

        Query<Player> query = sessionFactory.getCurrentSession().createQuery("from Player where name like :name", Player.class);
        query.setParameter("name", "%" + name + "%");
        List<Player> lstPlayer = query.list();
        
        // create a object of type PlayerType which takes player objects in its list
        PlayerListType playerListType = new PlayerListType();

        // iterate and set the values and return list
        for (Player player : lstPlayer) {
            // add player info
            PlayerType playerType = new PlayerType();
            playerType.setPlayerId(player.getPlayerId());
            playerType.setName(player.getName());
            playerType.setAge(player.getAge());
            playerType.setMatches(player.getMatches());
            playerListType.getPlayerType().add(playerType); // add to playerListType
        }
        return Response.ok(playerListType).build();
    }

    private Player findById(int id) {
        // retrieve player based on the id supplied in the formal argument
        return sessionFactory.getCurrentSession().get(Player.class, id);
    }
}
