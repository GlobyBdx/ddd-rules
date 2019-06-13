package fr.ubordeaux.ddd.example.infrastructure.evil.repositories;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.example.domain.model.evil.repositories.EvilRepositoryWithoutEntityValueObjectAccess;

/**
 * Repository implementation
 * 
 */

@Repository
public class EvilRepositoryImplementationWithoutEntityValueObjectAccess implements EvilRepositoryWithoutEntityValueObjectAccess {
	private Set<String> values;

	public EvilRepositoryImplementationWithoutEntityValueObjectAccess() {
		this.values = new HashSet<>();
	}

	@Override
	public void store(String value) {
		this.values.add(value);
	}

	@Override
	public Set<String> findAll() {
		return values;
	}

	@Override
	public Set<String> findByValue(String value) {
		Set<String> values = new HashSet<>();
		for (String storedValue : this.findAll()) {
			if (storedValue.compareTo(value) == 0) {
				values.add(storedValue);
			}
		}
		return values;
	}
}