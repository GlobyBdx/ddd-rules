package fr.ubordeaux.ddd.example.domain.model.evil;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

public class EvilTokenAccessingGoodValueObjectAConstructor {
	protected static GoodValueObjectA valueObject = new GoodValueObjectA("");
}