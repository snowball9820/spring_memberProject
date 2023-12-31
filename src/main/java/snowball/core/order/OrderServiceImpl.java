package snowball.core.order;

import snowball.core.discount.DiscountPolicy;
import snowball.core.member.Member;
import snowball.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{
    //아래 두개 필요 MemoryMemberRepository와 FixDiscountPolicy 구현체가 있어야지
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //final로 되어있으면 생성자를 통해 할당이 되어야 함
    //DIP를 지키고 있음
    //인터페이스에만 의존

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //private final DiscountPolicy discountPolicy=new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy=new RateDiscountPolicy();
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //일단 멤버 찾고
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        //설계가 잘된거임...할인에 대한건 discountPolicy 니가 알아서 해 =>단일 책임 원칙 준수

        //orderServiceImpl 역할 끝
        //조회후 할인 정책에다가 회원을 그냥 넘김
        //Grade만 넘길지 회원을 넘길지 고민하면 됨
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
