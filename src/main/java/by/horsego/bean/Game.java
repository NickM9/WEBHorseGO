package by.horsego.bean;

import java.util.List;
import java.util.Map;

public class Game extends Entity {

    private int id;
    private List<Horse> horses;
    private Map<Horse, List<BetType>> horseBetTypes;
    private boolean gamePlayed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void setHorses(List<Horse> horses) {
        this.horses = horses;
    }

    public Map<Horse, List<BetType>> getHorseBetTypes() {
        return horseBetTypes;
    }

    public void setHorseBetTypes(Map<Horse, List<BetType>> horseBetTypes) {
        this.horseBetTypes = horseBetTypes;
    }

    public boolean isGamePlayed() {
        return gamePlayed;
    }

    public void setGamePlayed(boolean gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != game.id) return false;
        if (gamePlayed != game.gamePlayed) return false;
        if (horses != null ? !horses.equals(game.horses) : game.horses != null) return false;
        return horseBetTypes != null ? horseBetTypes.equals(game.horseBetTypes) : game.horseBetTypes == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (horses != null ? horses.hashCode() : 0);
        result = 31 * result + (horseBetTypes != null ? horseBetTypes.hashCode() : 0);
        result = 31 * result + (gamePlayed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", horses=" + horses +
                ", horseBetTypes=" + horseBetTypes +
                ", gamePlayed=" + gamePlayed +
                '}';
    }
}
