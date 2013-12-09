package emul;

public class Constants {

    public static final double CELL_WEIGHT_MULTIPLIER = 0.1;            //0 10
    public static final double CELL_WEIGHT_MOVE_GAINER = 0.1;           //0 10
    public static final double LEAD_DISTANCE_TO_TEAM = 4;               //0 10
    public static final double TEAM_DISTANCE_TO_LEAD = 2;               //0 10
    public static final double STAND_STANCE_WEIGHT = 1.1;               //0 10
    public static final double KNEE_STANCE_WEIGHT = 1.1;                //0 10
    public static final double PRONE_STANCE_WEIGHT = 1.1;               //0 10
    public static final double MEDIC_HEAL_WEIGHT = 3;                   //0 20
    public static final double ENEMY_SHOOT_SIMPLE_WEIGHT = 1.5;         //-2 2
    public static final double GRAB_FOOD_BONUS_WEIGHT = 20;             //0 50
    public static final double GRAB_GRENADE_BONUS_WEIGHT = 20;          //0 50
    public static final double GRAB_MEDKIT_BONUS_WEIGHT = 20;           //0 50
    public static final double SHOOT_AND_HIDE_WEIGHT = 130;             //0 200
    public static final double DEAD_ENEMY_WEIGHT = 400;                 //0 300
    public static final double LEADER_DISTANCE_PENALTY = -40;           //-50 0
    public static final double FOLLOWER_DISTANCE_PENALTY = -40;         //-50 0
    public static final double FIELD_RATION_BONUS_WASTE_PENALTY = -30;  //-100 0
    public static final double SHOOT_AT_WEAKEST_BONUS_WEIGHT = 0.7;       //0 20
    public static final double SNIPER_CAN_SHOOT_GAIN = 20;              //0 200
    public static final double INJURED_VISIBLE_WEIGHT = 80;             //0 200
    public static final double ENEMY_CAN_THROW_GRENADE_PENALTY = -80;   //-100 0
    public static final double AVOID_DANGER_AREA_WEIGHT = 0.0;         //0 30
    public static final double ENEMY_EXPIRE_MOVES_COUNT = 30;
    public static final double HIDE_FROM_ENEMIES_WEIGHT = 50;
    public static final double FAKE_ENEMY_DECREASE_HITPOINTS_PENALTY = 1.3;
    public static final double DISTANCE_TO_ENEMIES_WEIGHT = 0.0000;
    public static final double LANDED_ON_SAME_POSITION_PENALTY = -2;
    public static final double CLOSE_TO_CENTER_MOVE_GAINER = 0.0;
    public static final double SNIPER_KNEE_STANCE_WEIGHT = 30;
    public static final double ENEMY_SNIPER_CAN_SEE_PENALTY = -80;
    public static final double HIDDEN_FROM_DANGER_AREA_WEIGHT = 1.1;
    public static final double CAN_SEE_DANGER_AREA_WEIGHT = 1.3;
    public static final double SUPPORT_CLOSE_TO_LEADER_PENALTY = -60;
    public static final double FAR_FROM_COMMANDER_PENALTY = -20;

}
