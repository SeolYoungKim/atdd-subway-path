package nextstep.subway.section.domain;

import javax.persistence.*;

import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private Integer distance;

    protected Section() {
    }

    public Section(Station upStation, Station downStation, Integer distance) {
        validateStation(upStation, downStation);
        validateDistance(distance);

        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    private void validateStation(Station upStation, Station downStation) {
        if (upStation.equals(downStation)) {
            throw new IllegalArgumentException("upStation과 downStation은 같을 수 없습니다.");
        }
    }

    private void validateDistance(Integer distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("구간 길이는 0보다 커야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Integer getDistance() {
        return distance;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Long getUpStationId() {
        return upStation.getId();
    }

    public String getUpStationName() {
        return upStation.getName();
    }

    public Station getDownStation() {
        return downStation;
    }

    public Long getDownStationId() {
        return downStation.getId();
    }

    public String getDownStationName() {
        return downStation.getName();
    }

    public void assignLine(Line line) {
        this.line = line;
    }

    public boolean hasOnlyOneSameStation(Section section) {
        return !hasAllSameStations(section)
                && hasSameUpStation(section)
                || hasSameDownStation(section);
    }

    public boolean hasAllSameStations(Section section) {
        return hasSameUpStation(section) && hasSameDownStation(section);
    }

    public boolean hasSameUpStation(Section section) {
        return upStation.equals(section.upStation);
    }

    public boolean hasSameUpStation(Station station) {
        return upStation.equals(station);
    }

    public boolean hasSameDownStation(Section section) {
        return downStation.equals(section.downStation);
    }

    public boolean hasSameDownStation(Station station) {
        return downStation.equals(station);
    }

    public boolean hasSameOrLongerDistance(Section newSection) {
        return distance.equals(newSection.distance) || distance < newSection.distance;
    }

    public boolean hasSameUpStationId(Long upStationId) {
        return upStation.hasEqualId(upStationId);
    }

    public boolean hasSameDownStationId(Long lastStationId) {
        return downStation.hasEqualId(lastStationId);
    }
}
