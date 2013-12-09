package emul.state;

import model.Trooper;
import model.TrooperType;
import model.World;
import util.WorldUtils;

import java.util.ArrayList;
import java.util.List;

public class TeammatesHolder {


    private List<Trooper> previous = new ArrayList<Trooper>();
    private List<Trooper> current = new ArrayList<Trooper>();

    public void update(World world) {
        this.previous = current;
        this.current = WorldUtils.getTeammates(world);
    }

    public boolean shotFromNowhere(TrooperType trooperType) {
        Trooper prev = findTrooper(trooperType, previous);
        Trooper self = findTrooper(trooperType, current);
        if (self.getHitpoints() < prev.getHitpoints()) {
            return true;
        }
        return false;
    }

    private Trooper findTrooper(TrooperType trooperType, List<Trooper> teammates) {
        for (Trooper trooper : teammates) {
            if (trooper.getType().equals(trooperType)) {
                return trooper;
            }
        }
        return null;
    }

}
