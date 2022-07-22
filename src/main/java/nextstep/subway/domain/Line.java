package nextstep.subway.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nextstep.subway.applicaion.dto.LineRequest;

@Entity
public class Line {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String color;

	@Embedded
	private Sections sections = new Sections();

	public Line() {
	}

	public Line(String name, String color) {
		this.name = name;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Section> getSections() {
		return sections.getSectionList();
	}

	public void addSection(Section section) {
		this.sections.addSection(section);
	}

	public List<Station> getStations() {
		return this.sections.getStations();
	}

	public void removeSection(Section section) {
		this.sections.remove(section);
	}

	public Section getLastSection() {
		return this.sections.getLastSection();
	}

	public void update(LineRequest updateReqDto) {
		if (updateReqDto.getName() != null) {
			this.name = updateReqDto.getName();
		}
		if (updateReqDto.getColor() != null) {
			this.color = updateReqDto.getColor();
		}
	}
}
