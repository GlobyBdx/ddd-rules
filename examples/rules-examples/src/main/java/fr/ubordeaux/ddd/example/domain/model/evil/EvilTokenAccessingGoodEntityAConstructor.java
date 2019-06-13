package fr.ubordeaux.ddd.example.domain.model.evil;

import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityA;

public class EvilTokenAccessingGoodEntityAConstructor {
	protected static GoodEntityA entity = new GoodEntityA(EvilTokenAccessingGoodValueObjectAConstructor.valueObject);
}