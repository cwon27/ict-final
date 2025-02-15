<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/inc/page_header.jspf"%>

<link rel="stylesheet" href="/css/mypage.css" type="text/css" />
<link rel="stylesheet" href="/css/mypage_cancel3.css" type="text/css" />
<script src="/js/mypage_common.js"></script>
<script src="/js/mypage_cancel3.js"></script>

<div class="mypage_wrap">
  <div class="mypage_main_wrap">
    <div class="mypage_left">
      <a href="/user/mypage" class="mypage_home">MY PAGE</a>
      <section class="mypage_menu">
        <h3>나의 쇼핑정보</h3>
        <ul>
          <li>
            <a href="/user/mypage_order" class="option_active">주문배송조회</a>
          </li>
          <li>
            <a href="/user/mypage_review"> 상품 리뷰 </a>
          </li>
        </ul>
        <h3>나의 계정설정</h3>
        <ul>
          <li>
            <a href="/user/mypage_userEdit">회원정보수정</a>
          </li>
          <li>
            <a href="/user/mypage_comm">내가 쓴 글</a>
          </li>
          <li>
            <a href="/user/mypage_point">적립금</a>
          </li>
          <li>
            <a href="/user/mypage_heart">좋아요</a>
          </li>
        </ul>
        <h3>고객센터</h3>
        <ul>
          <li>
            <a href="/user/mypage_qna">1:1 문의</a>
          </li>
          <li>
            <a href="">공지사항</a>
          </li>
          <li>
            <a href="">FAQ</a>
          </li>
        </ul>
      </section>

      <section>
        <div class="btn_bx">
          <button type="button" class="logout_btn" onclick="logout()">LOGOUT</button>
        </div>
      </section>
    </div>
    <div class="mypage_right">
      <ul class="mypage_right_top">
          <li class="user_inform">
            <a class="atag_css" href="/user/mypage_userEdit"
              ><strong>회원정보 수정</strong><span class="currentID"></span></a
            >
          </li>
          <li class="user_retention_details">
            <a class="atag_css" href="/user/mypage_point"
              ><strong class="">적립금</strong><span class="reservePoint"></span></a
            >
          </li>
          <li class="user_retention_details">
            <a class="atag_css" href="/user/mypage_review"
              ><strong class="">작성한 리뷰</strong><span class="afterCountSpan"></span></a
            >
          </li>
        </ul>

      <div class="mypage_right_element">
        <!-- 여기에 페이지에 맞는 요소 넣으면 됨 -->
        <ol class="cancelProcess">
          <li class="cancel_step">
            <p class="cancel_stepNo">취소상품 선택</p>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              color="primary"
              class="nextSvg"
            >
              <g id="weight=light, fill=false">
                <path
                  id="vector"
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M16.7071 12L8.35354 20.3536L7.64643 19.6464L15.2929 12L7.64643 4.35355L8.35354 3.64645L16.7071 12Z"
                  fill="black"
                ></path>
              </g>
            </svg>
          </li>
          <li class="cancel_step">
            <p class="cancel_stepNo">취소사유 작성</p>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              color="primary"
              class="nextSvg"
            >
              <g id="weight=light, fill=false">
                <path
                  id="vector"
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M16.7071 12L8.35354 20.3536L7.64643 19.6464L15.2929 12L7.64643 4.35355L8.35354 3.64645L16.7071 12Z"
                  fill="black"
                ></path>
              </g>
            </svg>
          </li>
          <li class="cancel_step">
            <p class="cancel_stepOk">취소정보 확인</p>
          </li>
        </ol>
        <div class="cancel_last_container">
          <section>
            <h2>취소 상품 정보</h2>
            <table>
              <thead>
                <tr>
                  <th>상품 정보</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="product" items="${cancelProduct}">
                    <tr>
                      <td>
                        <div class="cancel_product_info">
                          <img src="http://192.168.1.180:8000/${product.thumImg}" />
                          <div>
                            <p class="order_aniTitle">${product.ani_title}</p>
                            <strong>${product.title}</strong>
                            <p class="pro_price">
                                <span class="pro_price_span"></span>
                                / 수량 ${cancelProducts[product.pro_idx]}개
                            </p>
                          </div>
                        </div>
                      </td>
                    </tr>
                </c:forEach>
              </tbody>
            </table>
          </section>
          <section>
            <h2>취소 정보</h2>
            <div class="cancel_price_info">
              <dl>
                <dt class="order_price">
                  <span>결제금액</span>
                  <strong class="pay_totalPrice"></strong>
                </dt>
                <dd class="order_price_detail">
                  <ul>
                    <li>
                      <span>상품금액</span>
                      <span class="cancelProductPrice"></span>
                    </li>
                    <li>
                      <span>배송비</span>
                      <span class="refundDeliveryFee"></span>
                    </li>
                    <li>
                      <span>적립금할인</span>
                      <span class="order_usePoint"></span>
                    </li>
                  </ul>
                </dd>
              </dl>
              <dl>
                <dt class="order_price">
                  <span>환불예정금액</span>
                  <strong class="cancel_totalPrice"></strong>
                </dt>
                <dd class="order_price_detail">
                  <ul class="cancel_price_list">
                    <li>
                      <span>카드 환불</span>
                      <span class="cancel_amount"></span>
                    </li>
                    <li>
                      <span>적립금 환불</span>
                      <span class="cancel_point"></span>
                    </li>
                  </ul>
                </dd>
              </dl>
            </div>
            <div class="btn_div">
              <button id="cancel_input_btn">취소접수 완료하기</button>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
    let prices = [];
    let amounts = [];
    let cancelCounts = [];
    <c:forEach var="product" items="${cancelProduct}" varStatus="status">
        prices.push(${product.price});
        amounts.push(${product.amount});
        cancelCounts.push(${cancelProducts[product.pro_idx]});
    </c:forEach>
    let use_point = ${use_point};
    let refundDeliveryFee = ${refundDeliveryFee};
    let totalProductPrice = ${totalProductPrice};
    let balanceAmount = ${balanceAmount};
    let balancePoint = use_point-${balancePoint};
</script>

<%@include file="/WEB-INF/inc/footer.jspf"%>