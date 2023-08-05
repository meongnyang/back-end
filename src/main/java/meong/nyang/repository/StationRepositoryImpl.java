package meong.nyang.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Station;
import org.springframework.context.annotation.Primary;

import static meong.nyang.domain.QStation.*;
@Primary
@RequiredArgsConstructor
public class StationRepositoryImpl implements CustomStationRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public String findStationByLocation(Double x, Double y) {
        // latitude 를 radians 로 계산
        NumberExpression<Double> radiansLatitude =
                Expressions.numberTemplate(Double.class, "radians({0})", y);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosLatitude =
                Expressions.numberTemplate(Double.class, "cos({0})", radiansLatitude);
        NumberExpression<Double> cosSubwayLatitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", station.y);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinLatitude =
                Expressions.numberTemplate(Double.class, "sin({0})", radiansLatitude);
        NumberExpression<Double> sinSubWayLatitude =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))", station.y);

        // 사이 거리 계산
        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", station.x, x);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})", cosLatitude.multiply(cosSubwayLatitude).multiply(cosLongitude).add(sinLatitude.multiply(sinSubWayLatitude)));

        // 최종 계산
        NumberExpression<Double> distanceExpression =
                Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

        return jpaQueryFactory.select(station.name)
                .from(station)
                .orderBy(distanceExpression.asc())
                .limit(1)
                .fetchFirst();
    }

}
