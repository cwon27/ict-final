<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/WEB-INF/inc/store_header.jspf"%>


<link href="/css/storeList.css" rel="stylesheet" type="text/css">
<script src="../../../js/storeList.js"></script>

<div class="storeList_container">
        <h2>#hololive GAMERS 콜라보 굿즈 예약 판매 (~12.15)</h2>
            <section class="list-banners" id="banner">
                <div class="list-banner-images" id="List-div">
                    <div class="list-banner">
                            <img src="img/store/list-banner.png" alt="List Banner1">
                    </div>
                </div>
                </section>

                    <!-- 좌측 필터 -->
                    <section class="filter-section">  
                        <div class="left-con">
                            <h3>필터</h3>
                            <div class="filter-header">
                                <span>판매종료 포함</span>
                                <label class="switch">
                                    <input type="checkbox" id="stockFilter" onclick="applyFilters(this)">
                                    <span class="slider round"></span>
                                </label>
                            </div>
                            <hr>
                            <div class="filter-type-title">
                                  <ul class="filter-list">
                                    <!-- firstCategoryList를 순회하면서 카테고리 목록을 출력 -->
                                  <!-- <c:forEach var="category" items="${firstCategoryList}">
                                        <li class="filter-item" onclick="loadSubcategories(${category.code})">
                                       <span class="filter-text">${category.type}</span>
                                        </li>
                                    </c:forEach> -->
                                </ul>

                                  <!-- 하위 카테고리를 표시할 영역 -->
                                    <h3>하위 카테고리</h3>
                                    <ul id="subcategory-list" class="filter-list">
                                        <!-- 하위 카테고리 목록이 여기에 표시됩니다 -->

                                    </ul>
                            </div>
                            
                            </ul>
                            <hr>
                        </div>
                    </section>
                

                <!-- 상품 섹션 위쪽에 필터 추가 -->
                        <section class="product-filter">
                            <div class="filter-options">
                                <!-- 검색창 추가 -->
                                <div class="search-section">
                                    <div class="search_window">
                                    <input type="text" id="productSearch" class="search-input" placeholder="검색어를 입력하세요..." onkeyup="searchProducts()" />
                                    </div>
                                    <div class="search-icon">
                                        <img src="img/store/search_box_btn.png">
                                    </div>
                                </div>
                                <div class="filter-option-keyword">
                                    <span class="filter-option" onclick="filterProductsByType('latest')">최신순</span>
                                    <span class="filter-option" onclick="filterProductsByType('popular')">인기순</span>
                                    <span class="filter-option" onclick="filterProductsByType('high-price')">높은 가격순</span>
                                    <span class="filter-option" onclick="filterProductsByType('low-price')">낮은 가격순</span>
                                    
                                </div>
                            </div>
                        </section>


                <!--상품섹션-->
                <section class="list-products">
                    <div class="right-con">
                        <ul class="list-carousel">
                            <li class="list-carousel-wrapper">
                                <ul class="list-carousel-images">
                                  <!--db에서 가져온 상품목록-->
                                  <c:forEach var="product" items="${pagedProducts}">
                                    <li class="list-product">
                                        <!-- idx 값을 사용하여 링크 생성 -->
                                     <a href="<c:url value='/storeDetail/${product.idx}' />">
                                         <img src="http://192.168.1.92:8000/${product.thumImg}" alt="${product.title}">
                                     </a>

                                        <p>${product.title}</p>
                                        <p>    <fmt:formatNumber value="${product.price}" type="number" pattern="#,###"/> 원</p>
                                    </li>
                                </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </section>

 <div class="pagination">
               <!-- 이전 페이지 링크 -->
               <c:if test="${currentPage > 1}">
                   <a href="/storeList?pageNum=${currentPage - 1}">&laquo; 이전</a>
               </c:if>

               <!-- 페이지 번호 링크 -->
               <c:forEach var="i" begin="${currentPage - 2 > 0 ? currentPage - 2 : 1}" end="${currentPage + 2 < totalPages ? currentPage + 2 : totalPages}">
                   <c:choose>
                       <c:when test="${i == currentPage}">
                           <span class="current">${i}</span> <!-- 현재 페이지 -->
                       </c:when>
                       <c:otherwise>
                           <a href="/storeList?pageNum=${i}">${i}</a>
                       </c:otherwise>
                   </c:choose>
               </c:forEach>

               <!-- 다음 페이지 링크 -->
               <c:if test="${currentPage < totalPages}">
                   <a href="/storeList?pageNum=${currentPage + 1}">다음 &raquo;</a>
               </c:if>
           </div>




<%@include file="/WEB-INF/inc/store_footer.jspf"%>

