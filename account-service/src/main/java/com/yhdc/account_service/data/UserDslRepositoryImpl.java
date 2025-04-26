//package com.yhdc.account_server.data;
//
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.support.PageableExecutionUtils;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.yhdc.account_server.data.QUser.user;
//
//
//@RequiredArgsConstructor
//@Repository
//public class UserDslRepositoryImpl implements UserDslRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//
//    /**
//     * USER SEARCH QUERY
//     *
//     * @param keyword
//     * @param pageable
//     * @apiNote Search user by keyword then return result page
//     */
//    @Override
//    public Page<User> searchUser(String keyword, Pageable pageable) {
//
//        List<User> searchList = jpaQueryFactory
//                .selectFrom(user)
//                .where(user.username.contains(keyword)
//                        .or(user.firstName.contains(keyword))
//                        .or(user.lastName.contains(keyword))
//                        .or(user.email.contains(keyword))
//                        .or(user.phone.contains(keyword)))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> count = jpaQueryFactory
//                .select(user.count())
//                .from(user)
//                .where(user.username.contains(keyword)
//                        .or(user.firstName.contains(keyword))
//                        .or(user.lastName.contains(keyword))
//                        .or(user.email.contains(keyword))
//                        .or(user.phone.contains(keyword)));
//
////        return new PageImpl<>(searchList, pageable, count);
//        // PageableExecutionUtils을 사용하면 단순 new PageImpl()을 사용했을 때 보다 성능 최적화 이점이 있습니다.
//        //PageableExecutionUtils 클래스는 내부에 getPage()라는 단 하나의 (정적) 메서드를 가집니다.
//        return PageableExecutionUtils.getPage(searchList, pageable, count::fetchOne);
//
//    }
//
//}
