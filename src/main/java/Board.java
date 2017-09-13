public class Board {

	private Long id;

	private String name;

	private Boolean isOpen = true;

	public Board() {

	}

	public Boolean getIsOpen() {

		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {

		this.isOpen = isOpen;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Board board = (Board) o;

		if (id != null ? !id.equals(board.id) : board.id != null) return false;
		return name != null ? name.equals(board.name) : board.name == null;
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {

		return id + ": " + name;
	}
}
