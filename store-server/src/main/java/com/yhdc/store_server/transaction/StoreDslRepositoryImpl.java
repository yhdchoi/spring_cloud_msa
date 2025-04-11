package com.yhdc.store_server.transaction;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.yhdc.store.transaction.QStore.store;


@RequiredArgsConstructor
public class StoreDslRepositoryImpl implements StoreDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Store> searchStore(String keyword, Pageable pageable) {
        List<Store> searchList = jpaQueryFactory
                .selectFrom(store)
                .where(store.name.contains(keyword))
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(store.count())
                .from(store)
                .where(store.name.contains(keyword));

//        return new PageImpl<>(searchList, pageable, count);
        // PageableExecutionUtils을 사용하면 단순 new PageImpl()을 사용했을 때 보다 성능 최적화 이점이 있습니다.
        //PageableExecutionUtils 클래스는 내부에 getPage()라는 단 하나의 (정적) 메서드를 가집니다.
        return PageableExecutionUtils.getPage(searchList, pageable, count::fetchOne);

    }

}
