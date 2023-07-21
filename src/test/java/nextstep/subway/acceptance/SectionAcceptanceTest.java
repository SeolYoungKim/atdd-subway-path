package nextstep.subway.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.subway.acceptance.step.LineStep;
import nextstep.subway.acceptance.step.SectionStep;
import nextstep.subway.acceptance.step.StationStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("지하철 구간 관련 기능")
public class SectionAcceptanceTest extends AcceptanceTest {
    /**
     * Given : 지하철역을 3개 생성하고
     * And : 지하철 노선을 1개 생성한 후
     * When : 역 사이에 새로운 구간을 등록하면
     * Then : 노선에 새로운 구간이 등록된다
     */
    @DisplayName("역 사이에 새로운 지하철 구간 등록")
    @Test
    void registerSectionBetweenStation() {
        // given
        long 노선_상행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 노선_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재시민의숲역"));
        long 구간_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(노선_상행_Id, 노선_하행_Id, "신분당선", 10));

        // when
        ExtractableResponse<Response> createSectionResponse = SectionStep.지하철_노선_구간을_등록한다(lineId, 노선_상행_Id, 구간_하행_Id, 5);

        // then
        assertThat(createSectionResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> showLineResponse = LineStep.지하철_노선을_조회한다(lineId);
        List<String> 상행역_이름_목록 = 지하철_구간_목록의_상행역_이름을_추출한다(showLineResponse);

        assertThat(상행역_이름_목록).hasSize(2);
        assertThat(상행역_이름_목록.get(0)).isEqualTo("강남역");
        assertThat(상행역_이름_목록.get(1)).isEqualTo("양재역");
    }

    /**
     * Given : 지하철역을 3개 생성하고
     * And : 지하철 노선을 1개 생성한 후
     * When : 상행 종점에 새로운 구간을 등록하면
     * Then : 노선에 새로운 구간이 등록된다
     */
    @DisplayName("상행 종점에 새로운 지하철 구간 등록")
    @Test
    void registerSectionUpStation() {
        // given
        long 노선_상행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 노선_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));
        long 구간_상행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("신논현역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(노선_상행_Id, 노선_하행_Id, "신분당선", 10));

        // when
        ExtractableResponse<Response> createSectionResponse = SectionStep.지하철_노선_구간을_등록한다(lineId, 구간_상행_Id, 노선_상행_Id, 5);

        // then
        assertThat(createSectionResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> showLineResponse = LineStep.지하철_노선을_조회한다(lineId);
        List<String> 상행역_이름_목록 = 지하철_구간_목록의_상행역_이름을_추출한다(showLineResponse);

        assertThat(상행역_이름_목록).hasSize(2);
        assertThat(상행역_이름_목록.get(0)).isEqualTo("신논현역");
        assertThat(상행역_이름_목록.get(1)).isEqualTo("강남역");
    }

    /**
     * Given : 지하철역을 3개 생성하고
     * And : 지하철 노선을 1개 생성한 후
     * When : 하행 종점에 새로운 구간을 등록하면
     * Then : 노선에 새로운 구간이 등록된다
     */
    @DisplayName("하행 종점에 새로운 지하철 구간 등록")
    @Test
    void registerSectionDownStation() {
        // given
        long 노선_상행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 노선_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));
        long 구간_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재시민의숲역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(노선_상행_Id, 노선_하행_Id, "신분당선", 10));

        // when
        ExtractableResponse<Response> createSectionResponse = SectionStep.지하철_노선_구간을_등록한다(lineId, 노선_하행_Id, 구간_하행_Id, 5);

        // then
        assertThat(createSectionResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> showLineResponse = LineStep.지하철_노선을_조회한다(lineId);
        List<String> 하행역_이름_목록 = 지하철_구간_목록의_하행역_이름을_추출한다(showLineResponse);

        assertThat(하행역_이름_목록).hasSize(2);
        assertThat(하행역_이름_목록.get(0)).isEqualTo("양재역");
        assertThat(하행역_이름_목록.get(1)).isEqualTo("양재시민의숲역");
    }

    private long 응답_결과에서_Id를_추출한다(ExtractableResponse<Response> responseOfCreateStation) {
        return responseOfCreateStation.jsonPath().getLong("id");
    }

    private List<String> 지하철_구간_목록의_상행역_이름을_추출한다(ExtractableResponse<Response> showLineResponse) {
        return showLineResponse.jsonPath().getList("sections.upStationName");
    }

    private List<String> 지하철_구간_목록의_하행역_이름을_추출한다(ExtractableResponse<Response> showLineResponse) {
        return showLineResponse.jsonPath().getList("sections.downStationName");
    }

    /*
     * 지하철 구간 삭제 기능 개선
     * # 변경된 스펙
     * 구간 삭제에 대한 제약 사항 변경 구현
     * 기존에는 마지막 역 삭제만 가능했는데 위치에 상관 없이 삭제가 가능하도록 수정
     *
     * 종점이 제거될 경우 다음으로 오던 역이 종점이 됨
     *
     * 중간역이 제거될 경우 재배치를 함
     * 노선에 A - B - C 역이 연결되어 있을 때 B역을 제거할 경우 A - C로 재배치 됨
     * - ex: 강남역 - 양재역 - 양재시민의숲역 -> 양재역 제거 -> 강남역 - 양재시민의숲역 (구간이 2개에서 1개로 줄어듦)
     * 거리는 두 구간의 거리의 합으로 정함
     *
     * 구간이 하나인 노선에서 마지막 구간을 제거할 때 -> 제거할 수 없음
     *
     * 이 외 예외 케이스를 고려하기
     * 기능 설명을 참고하여 예외가 발생할 수 있는 경우를 검증할 수 있는 인수 테스트를 만들고 이를 성공 시키세요.
     * 예시) 노선에 등록되어있지 않은 역을 제거하려 한다.
     */


    /**
     * Given : 지하철역을 3개 생성하고
     * And : 지하철 노선을 1개 생성하고
     * And : 새로운 구간을 1개 등록한 후
     * When : 가운데 역을 제거하면
     * Then : 구간이 삭제된다
     */
    @DisplayName("지하철 구간 삭제 : 가운데 역 제거")
    @Test
    void deleteSectionMidStation() {
        // given
        long 상행종점_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 중간역_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));
        long 하행종점_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재시민의숲역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(상행종점_Id, 중간역_Id, "신분당선", 10));

        SectionStep.지하철_노선_구간을_등록한다(lineId, 중간역_Id, 하행종점_Id, 5);

        // when
        ExtractableResponse<Response> deleteSectionResponse = SectionStep.지하철_구간을_삭제한다(lineId, 중간역_Id);

        // then
        assertThat(deleteSectionResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> showLineResponse = LineStep.지하철_노선을_조회한다(lineId);
        List<String> 상행역_이름_목록 = 지하철_구간_목록의_상행역_이름을_추출한다(showLineResponse);
        List<String> 하행역_이름_목록 = 지하철_구간_목록의_하행역_이름을_추출한다(showLineResponse);

        assertThat(상행역_이름_목록).hasSize(1);
        assertThat(상행역_이름_목록.get(0)).isEqualTo("강남역");
        assertThat(하행역_이름_목록.get(0)).isEqualTo("양재시민의숲역");
    }

    /**
     * Given : 지하철역을 3개 생성하고
     * And : 지하철 노선을 1개 생성하고
     * And : 새로운 구간을 1개 등록한 후
     * When : 상행 종점 역을 제거하면
     * Then : 구간이 삭제된다
     */
    @DisplayName("지하철 구간 삭제 : 상행 종점 역 제거")
    @Test
    void deleteSectionFirstStation() {
        // given
        long 상행종점_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 중간역_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));
        long 하행종점_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재시민의숲역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(상행종점_Id, 중간역_Id, "신분당선", 10));

        SectionStep.지하철_노선_구간을_등록한다(lineId, 중간역_Id, 하행종점_Id, 5);

        // when
        ExtractableResponse<Response> deleteSectionResponse = SectionStep.지하철_구간을_삭제한다(lineId, 상행종점_Id);

        // then
        assertThat(deleteSectionResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> showLineResponse = LineStep.지하철_노선을_조회한다(lineId);
        List<String> 상행역_이름_목록 = 지하철_구간_목록의_상행역_이름을_추출한다(showLineResponse);
        List<String> 하행역_이름_목록 = 지하철_구간_목록의_하행역_이름을_추출한다(showLineResponse);

        assertThat(상행역_이름_목록).hasSize(1);
        assertThat(상행역_이름_목록.get(0)).isEqualTo("양재역");
        assertThat(하행역_이름_목록.get(0)).isEqualTo("양재시민의숲역");
    }

    /**
     * Given : 지하철역을 3개 생성하고
     * And : 지하철 노선을 1개 생성하고
     * And : 새로운 구간을 1개 등록한 후
     * When : 하행 종점 역을 제거하면
     * Then : 구간이 삭제된다
     */
    @DisplayName("지하철 구간 삭제 : 하행 종점 역 제거")
    @Test
    void deleteSectionLastStation() {
        // given
        long 상행종점_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 중간역_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));
        long 하행종점_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재시민의숲역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(상행종점_Id, 중간역_Id, "신분당선", 10));

        SectionStep.지하철_노선_구간을_등록한다(lineId, 중간역_Id, 하행종점_Id, 5);

        // when
        ExtractableResponse<Response> deleteSectionResponse = SectionStep.지하철_구간을_삭제한다(lineId, 하행종점_Id);

        // then
        assertThat(deleteSectionResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> showLineResponse = LineStep.지하철_노선을_조회한다(lineId);
        List<String> 상행역_이름_목록 = 지하철_구간_목록의_상행역_이름을_추출한다(showLineResponse);
        List<String> 하행역_이름_목록 = 지하철_구간_목록의_하행역_이름을_추출한다(showLineResponse);

        assertThat(상행역_이름_목록).hasSize(1);
        assertThat(상행역_이름_목록.get(0)).isEqualTo("강남역");
        assertThat(하행역_이름_목록.get(0)).isEqualTo("양재역");
    }

    /**
     * Given : 지하철역을 2개 생성하고
     * And : 지하철 노선을 1개 생성한 후
     * When : 지하철 역을 제거하면
     * Then : 예외가 발생한다.
     */
    @DisplayName("지하철 구간 삭제 예외 케이스 : 구간이 1개일 때 제거")
    @Test
    void deleteSectionFailCaseOnlyOneSection() {
        // given
        long 노선_상행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 노선_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(노선_상행_Id, 노선_하행_Id, "신분당선", 10));

        // when
        ExtractableResponse<Response> deleteSectionResponse = SectionStep.지하철_구간을_삭제한다(lineId, 노선_하행_Id);
        
        // then
        assertThat(deleteSectionResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Given : 지하철역을 2개 생성하고
     * And : 지하철 노선을 1개 생성한 후
     * When : 없는 지하철 역을 제거하면
     * Then : 예외가 발생한다.
     */
    @DisplayName("지하철 구간 삭제 예외 케이스 : 없는 역을 제거하려 할 때")
    @Test
    void deleteSectionFailCaseNotHaveStation() {
        // given
        long 노선_상행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("강남역"));
        long 노선_하행_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("양재역"));
        long 노선에_없는_역_Id = 응답_결과에서_Id를_추출한다(StationStep.지하철역을_생성한다("없는역"));

        long lineId = 응답_결과에서_Id를_추출한다(LineStep.지하철_노선을_생성한다(노선_상행_Id, 노선_하행_Id, "신분당선", 10));

        // when
        ExtractableResponse<Response> deleteSectionResponse = SectionStep.지하철_구간을_삭제한다(lineId, 노선에_없는_역_Id);

        // then
        assertThat(deleteSectionResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
