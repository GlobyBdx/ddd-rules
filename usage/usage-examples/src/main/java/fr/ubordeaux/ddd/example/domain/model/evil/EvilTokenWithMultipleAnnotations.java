package fr.ubordeaux.ddd.example.domain.model.evil;

import fr.ubordeaux.ddd.annotations.types.Aggregate;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.annotations.types.Service;
import fr.ubordeaux.ddd.annotations.types.ValueObject;

@Aggregate
@Entity
@Factory
@Repository
@Service
@ValueObject
public class EvilTokenWithMultipleAnnotations {}