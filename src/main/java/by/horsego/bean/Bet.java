package by.horsego.bean;

public class Bet extends Entity{

    private int id;
    private int userId;
    private int horseId;
    private int gameId;
    private double betAmount;
    private BetType betType;
    private boolean userWin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public boolean isUserWin() {
        return userWin;
    }

    public void setUserWin(boolean userWin) {
        this.userWin = userWin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;

        if (id != bet.id) return false;
        if (userId != bet.userId) return false;
        if (horseId != bet.horseId) return false;
        if (gameId != bet.gameId) return false;
        if (Double.compare(bet.betAmount, betAmount) != 0) return false;
        if (userWin != bet.userWin) return false;
        return betType != null ? betType.equals(bet.betType) : bet.betType == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + userId;
        result = 31 * result + horseId;
        result = 31 * result + gameId;
        temp = Double.doubleToLongBits(betAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (betType != null ? betType.hashCode() : 0);
        result = 31 * result + (userWin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", userId=" + userId +
                ", horseId=" + horseId +
                ", gameId=" + gameId +
                ", betAmount=" + betAmount +
                ", betType=" + betType +
                ", userWin=" + userWin +
                '}';
    }
}
