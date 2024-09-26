<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>DashBoard - 굿즈 목록</title>
<link href="/css/masterStyle.css" rel="stylesheet" type="text/css"></link>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/MasterPage.js"></script>
<div class="mastermain">
    <nav class="navbar navbar-dark bg-dark">
        <div class="management"><a href="/master/masterMain">관리자 Management</a></div>
        <div>
            <div class="manage_menu">
                <ul>
                    <a href="" class="menu-item">회원관리</a>
                    <li class="submenu"><a href="/master/userMasterList">회원목록</a></li>
                    <li class="submenu"><a href="/master/reporinguserMasterList">신고계정목록</a></li>
                </ul>
                <ul>
                    <a href="#" class="menu-item">애니관리</a>
                    <li class="submenu"><a href="/master/aniMasterList">애니목록</a></li>
                </ul>
                <ul>
                    <a href="#" class="menu-item">굿즈관리</a>
                    <li class="submenu"><a href="/master/storeMasterList">굿즈목록</a></li>
                </ul>
                <ul>
                    <a href="#" class="menu-item">주문관리</a>
                    <li class="submenu"><a href="/master/orderMasterList">주문내역리스트</a></li>
                    <li class="submenu"><a href="#">일/월별 매출내역</a></li>
                </ul>
                <ul>
                    <a href="#" class="menu-item">신고관리</a>
                    <li class="submenu"><a href="#">신고목록</a></li>
                </ul>
                <ul>
                    <a href="#" class="menu-item">게시판,댓글, 리뷰관리</a>
                    <li class="submenu"><a href="#">게시판</a></li>
                    <li class="submenu"><a href="#">댓글</a></li>
                    <li class="submenu"><a href="#">리뷰</a></li>
                </ul>
                <ul>
                    <a href="#" class="menu-item">기타관리</a>
                    <li class="submenu"><a href="#">공지사항</a></li>
                    <li class="submenu"><a href="#">문의사항</a></li>
                    <li class="submenu"><a href="#">FAQ</a></li>
                    <li class="submenu"><a href="#">이벤트</a></li>
                </ul>
        </div>
        </div>
    </nav>
</div>
<div class="store-list-container">
    <h2>굿즈 전체 목록</h2>
    <table class="store-list">
        <thead>
            <tr>
                <th>카테고리</th>
                <th>애니 타이틀</th>
                <th>제목</th>
                <th>가격</th>
                <th>브랜드</th>
                <th>개수</th>
                <th>작업</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>
                    <button class="add-btn">추가</button>
                    <button class="edit-btn">수정</button>
                    <button class="delete-btn">삭제</button>
                </td>
            </tr>
            <tr>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>데이터 바인딩</td>
                <td>
                    <button class="add-btn">추가</button>
                    <button class="edit-btn">수정</button>
                    <button class="delete-btn">삭제</button>
                </td>
            </tr>
            <tr>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                <td>
                    <button class="add-btn">추가</button>
                    <button class="edit-btn">수정</button>
                    <button class="delete-btn">삭제</button>
                </td>
            </tr>
            <tr>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                            <td>데이터 바인딩</td>
                <td>
                    <button class="add-btn">추가</button>
                    <button class="edit-btn">수정</button>
                    <button class="delete-btn">삭제</button>
                </td>
            </tr>
        </tbody>
    </table>
        <div class="storePageing">페이징 영역</div>