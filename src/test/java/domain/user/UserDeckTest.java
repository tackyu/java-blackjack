package domain.user;

import domain.card.Card;
import domain.card.Number;
import domain.card.Shape;
import domain.deck.UserDeck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDeckTest {
    @Test
    @DisplayName("유저 카드 덱에 카드를 추가할 수 있다.")
    void pushCardTest() {
        Card card = new Card(Shape.CLOVER, Number.ACE);
        UserDeck userDeck = new UserDeck();

        userDeck.addCard(card);

        assertThat(userDeck.getCards()).contains(card);
    }

    @Test
    @DisplayName("덱 카드의 숫자의 합을 구할 수 있다.")
    void sumCardTest() {
        UserDeck userDeck = new UserDeck();

        userDeck.addCard(new Card(Shape.CLOVER, Number.THREE));
        userDeck.addCard(new Card(Shape.CLOVER, Number.EIGHT));

        assertThat(userDeck.sumCard()).isEqualTo(11);
    }

    @Test
    @DisplayName("ACE 카드 유무 판별")
    void hasAceTest() {
        UserDeck userDeck = new UserDeck();

        userDeck.addCard(new Card(Shape.CLOVER, Number.ACE));

        assertThat(userDeck.hasAce()).isTrue();
    }

    @Test
    @DisplayName("ACE 카드는 합이 11 이하일 때 숫자가 11로 사용된다.")
    void sumCardContainingAceTest() {
        UserDeck userDeck = new UserDeck();

        userDeck.addCard(new Card(Shape.CLOVER, Number.ACE));
        userDeck.addCard(new Card(Shape.CLOVER, Number.TWO));

        assertThat(userDeck.sumCard()).isEqualTo(13);
    }

    @Test
    @DisplayName("카드 덱의 결과합이 버스트 됐는지 확인한다")
    void isBustTest() {
        UserDeck userDeck = new UserDeck();
        userDeck.addCard(new Card(Shape.CLOVER, Number.FIVE));
        userDeck.addCard(new Card(Shape.CLOVER, Number.JACK));
        userDeck.addCard(new Card(Shape.CLOVER, Number.JACK));

        assertThat(userDeck.isBust()).isTrue();
    }
}
