package co.za.oneup.service.objects;

import java.util.LinkedList;
import java.util.List;

public class Hand {
	private List<Card> cards; // 4 or 6 cards max

	public List<Card> getCards() {
		if (cards == null) cards = new LinkedList<Card>();
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
