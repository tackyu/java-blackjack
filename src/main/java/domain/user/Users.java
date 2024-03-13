package domain.user;

import domain.card.Card;
import domain.deck.TotalDeck;
import domain.game.Index;
import domain.game.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static domain.game.Result.LOSE;
import static domain.game.Result.WIN;

public class Users {
    private static final int DEALER_INDEX = 0;
    private static final int FIRST_PLAYER_INDEX = 1;
    private final List<User> users;

    public Users(List<Player> players) {
        validateDuplicatedName(players);
        this.users = new ArrayList<>();
        users.add(new Dealer());
        users.addAll(players);
    }

    private void validateDuplicatedName(List<Player> players) {
        long count = players.stream()
                .map(player -> player.getName().value())
                .distinct()
                .count();
        if (players.size() != count) {
            throw new IllegalArgumentException("중복된 이름은 허용하지 않습니다.");
        }
    }

    public void setStartCards(TotalDeck totalDeck) {
        for (User user : users) {
            user.addCard(totalDeck.getNewCard());
            user.addCard(totalDeck.getNewCard());
        }
    }

    public Index makeIndex() {
        return new Index(users.size());
    }

    public User getUserByIndex(Index index) {
        return users.get(index.getCurrent());
    }

    public void addCardOfCurrentUser(Card newCard, Index index) {
        users.get(index.getCurrent()).addCard(newCard);
    }

    public boolean isDealerCardAddCondition() {
        return getDealer().isDealerCardAddable();
    }

    public void addDealerCard(Card card) {
        getDealer().addCard(card);
    }

    public Result generatePlayerResult(Player player) {
        if (player.isBust()) {
            return LOSE;
        }
        if (getDealer().isBust()) {
            return WIN;
        }
        return Result.compare(player.sumUserDeck(), getDealer().sumUserDeck());
    }

    public boolean currentUserBusted(Index index) {
        return users.get(index.getCurrent()).isBust();
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public List<Player> getPlayers() {
        List<User> players = users.subList(FIRST_PLAYER_INDEX, users.size());
        return players.stream()
                .map(user -> (Player) user)
                .collect(Collectors.toList());
    }

    public Dealer getDealer() {
        return (Dealer) users.get(DEALER_INDEX);
    }
}
