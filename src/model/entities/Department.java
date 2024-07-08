package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable{ 	//implements serializable, faz os Objetos virarem sequencias de bites, em Java tem que fazer o serializable caso que co objeto seja trafegado em arquivo e rede


	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	
	public Department() {
	}

	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() { //FACILITA A IMPRESSÃO DOS VALORES DO OBJETO
		return "Department [id=" + id + ", name=" + name + "]";
	}
}
