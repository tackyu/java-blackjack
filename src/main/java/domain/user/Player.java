package domain.user;

import domain.money.Money;

public class Player extends User {
    private final Money betAmount;

    public Player(Name name, Money betAmount) {
        super(name);
        this.betAmount = betAmount;
    }

    public Money getBetAmount() {
        return betAmount;
    }
}
