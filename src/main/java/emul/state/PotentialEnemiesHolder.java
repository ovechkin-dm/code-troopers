package emul.state;

import emul.Constants;
import model.Trooper;
import model.TrooperStance;
import model.World;
import util.WorldUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PotentialEnemiesHolder {


    private List<TemporaryEnemy> temporaryEnemies = new ArrayList<TemporaryEnemy>();
    private List<TemporaryEnemy> proneEnemies = new ArrayList<TemporaryEnemy>();

    public void update(World world) {
        cleanEnemies(world);
        for (Trooper enemy : WorldUtils.getEnemies(world)) {
            temporaryEnemies.add(new TemporaryEnemy(0, enemy));
        }
    }

    private void cleanEnemies(World world) {
        Iterator<TemporaryEnemy> iter = temporaryEnemies.iterator();
        while (iter.hasNext()) {
            TemporaryEnemy enemy = iter.next();
            if (existsInWorld(enemy.getSource(), world)) {
                iter.remove();
                removeFromProne(enemy);
            } else if (cellIsClean(world, enemy.getSource())) {
                if (visibleInProne(world, enemy.getSource(), TrooperStance.PRONE)) {
                    iter.remove();
                } else {
                    iter.remove();
                    proneEnemies.add(new TemporaryEnemy(-3, enemy.getSource()));
                }
            }
        }
    }

    private void removeFromProne(TemporaryEnemy enemy) {
        Iterator<TemporaryEnemy> prones = proneEnemies.iterator();
        while (prones.hasNext()) {
            Trooper current = prones.next().getSource();
            if (current.getId() == enemy.getSource().getId()) {
                prones.remove();
            }
        }
    }

    private boolean cellIsClean(World world, Trooper enemy) {
        for (Trooper teammate : WorldUtils.getTeammates(world)) {
            boolean visible = world.isVisible(teammate.getVisionRange(),
                    teammate.getX(), teammate.getY(), teammate.getStance(),
                    enemy.getX(), enemy.getY(), enemy.getStance());
            if (visible) {
                return true;
            }

        }
        return false;
    }


    private boolean visibleInProne(World world, Trooper enemy, TrooperStance stance) {
        for (Trooper teammate : WorldUtils.getTeammates(world)) {
            boolean visible = world.isVisible(teammate.getVisionRange(),
                    teammate.getX(), teammate.getY(), teammate.getStance(),
                    enemy.getX(), enemy.getY(), stance);
            if (visible) {
                return true;
            }

        }
        return false;
    }


    private boolean existsInWorld(Trooper enemy, World world) {
        for (Trooper trooper : WorldUtils.getEnemies(world)) {
            if (enemy.getId() == trooper.getId()) {
                return true;
            }
        }
        return false;
    }

    public void expire(World world) {
        Iterator<TemporaryEnemy> iter = temporaryEnemies.iterator();
        while (iter.hasNext()) {
            TemporaryEnemy enemy = iter.next();
            enemy.increaseLive();
            if (enemy.isExpired()) {
                iter.remove();
            }
        }
        expireProne(world);
    }

    public void expireProne(World world) {
        Iterator<TemporaryEnemy> iter = proneEnemies.iterator();
        while (iter.hasNext()) {
            TemporaryEnemy enemy = iter.next();
            enemy.increaseLive();
            if (enemy.isExpired()) {
                iter.remove();
            }
        }
    }

    public boolean isFake(Trooper trooper, World world) {
        for (Trooper enemy : WorldUtils.getEnemies(world)) {
            if (enemy.getId() == trooper.getId()) {
                return false;
            }
        }
        return true;
    }

    public List<Trooper> getEnemies() {
        List<Trooper> result = new ArrayList<Trooper>();
        for (TemporaryEnemy enemy : temporaryEnemies) {
            result.add(enemy.getSource());
        }
        return result;
    }

    private static class TemporaryEnemy {

        private int live;
        private Trooper source;

        private TemporaryEnemy(int live, Trooper source) {
            this.live = live;
            this.source = source;
        }

        private int getLive() {
            return live;
        }

        private Trooper getSource() {
            return source;
        }

        private void increaseLive() {
            live++;
        }

        private boolean isExpired() {
            return live >= Constants.ENEMY_EXPIRE_MOVES_COUNT;
        }

    }

    public List<Trooper> getProneEnemies() {
        List<Trooper> result = new ArrayList<Trooper>();
        for (TemporaryEnemy enemy : proneEnemies) {
            result.add(enemy.getSource());
        }
        return result;
    }

}
