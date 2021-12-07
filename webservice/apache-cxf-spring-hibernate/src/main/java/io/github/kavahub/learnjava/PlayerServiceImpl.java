package io.github.kavahub.learnjava;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Web Services 接口实现
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

        // 创建需要保存的实体
        Player playerForCreate = new Player();
        // 转换
        playerForCreate.setName(playerType.getName());
        playerForCreate.setAge(playerType.getAge());
        playerForCreate.setMatches(playerType.getMatches());

        // 持久化，返回主键
        int playerId = (Integer) sessionFactory.getCurrentSession().save(playerForCreate);
        return Response.status(Status.CREATED).entity(playerId).build();
    }


    @Transactional
    @Override
    public Response getById(int id) {

        Player player = this.findById(id);
        if (player == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // 传话
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

        sessionFactory.getCurrentSession().delete(playerForDelete);
        return Response.status(Status.NO_CONTENT).build();
    }



    @Transactional
    @Override
    public Response getByName(String name) {

        // 创建查询对象
        Query<Player> query = sessionFactory.getCurrentSession().createQuery("from Player where name like :name", Player.class);
        query.setParameter("name", "%" + name + "%");
        List<Player> lstPlayer = query.list();
        
        // 转换
        PlayerListType playerListType = new PlayerListType();
        for (Player player : lstPlayer) {
            PlayerType playerType = new PlayerType();
            playerType.setPlayerId(player.getPlayerId());
            playerType.setName(player.getName());
            playerType.setAge(player.getAge());
            playerType.setMatches(player.getMatches());
            playerListType.getPlayerType().add(playerType);
        }
        return Response.ok(playerListType).build();
    }

    private Player findById(int id) {
        
        return sessionFactory.getCurrentSession().get(Player.class, id);
    }
}
