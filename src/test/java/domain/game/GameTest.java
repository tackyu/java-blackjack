package domain.game;

import domain.card.Card;
import domain.card.Number;
import domain.card.Shape;
import domain.deck.TotalDeck;
import domain.deck.TotalDeckGenerator;
import domain.money.Money;
import domain.user.Name;
import domain.user.Player;
import domain.user.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static domain.game.Command.YES;
import static domain.game.Result.LOSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GameTest {
    @Test
    @DisplayName("게임이 생성될 때 각 유저에게 카드를 두 장씩 나눠준다.")
    void gameConstructTest() {
        TotalDeck totalDeck = TotalDeckGenerator.generate();
        Player player = new Player(new Name("a"), new Money("100"));
        Users users = new Users(List.of(player));

        new Game(totalDeck, users);

        assertThat(totalDeck.size()).isEqualTo(48);
    }

    @Test
    @DisplayName("입력이 y이면 현재 유저가 카드를 추가로 받는다.")
    void hitTest() {
        TotalDeck totalDeck = TotalDeckGenerator.generate();
        Player player = new Player(new Name("a"), new Money("100"));
        Users users = new Users(List.of(player));
        Game game = new Game(totalDeck, users);
        game.determineState(YES, player);

        assertThat(player.getUserDeck().getCards()).hasSize(3);
    }

    @Test
    @DisplayName("결과를 반환한다.")
    void gameResultTest() {
        TotalDeck totalDeck = new TotalDeck(List.of(
                new Card(Shape.CLOVER, Number.FIVE),
                new Card(Shape.CLOVER, Number.SIX),
                new Card(Shape.CLOVER, Number.SEVEN),
                new Card(Shape.CLOVER, Number.EIGHT)
        ));
        Player player = new Player(new Name("a"), new Money("100"));
        Users users = new Users(List.of(player));

        Game game = new Game(totalDeck, users);
        Map<Player, Result> playerResults = game.generatePlayerResults();

        assertAll(
                () -> assertThat(playerResults.get(player)).isEqualTo(LOSE)
        );
    }
}
