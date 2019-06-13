package fr.ubordeaux.ddd.example.domain.model.evil.repositories;

import java.util.Set;

/**
 * Repository interface
 * 
 */

public interface EvilRepositoryWithoutEntityValueObjectAccess {
	public void store(String value);
	public Set<String> findAll();
	public Set<String> findByValue(String value);
}