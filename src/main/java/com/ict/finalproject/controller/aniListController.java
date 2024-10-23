package com.ict.finalproject.controller;

import com.ict.finalproject.JWT.JWTUtil;
import com.ict.finalproject.Service.AniListService;
import com.ict.finalproject.vo.AniListVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Controller
public class aniListController {

    @Autowired
    private AniListService aniListService;

    @Autowired
    private JWTUtil jwtUtil;

    // 애니메이션 목록 조회

    @GetMapping("/aniList")
    public String getAniList(Model model) {
        List<AniListVO> aniAllList = aniListService.getAniList();
        model.addAttribute("aniAllList", aniAllList);
//        log.info(aniAllList.toString());

        return "ani/aniList"; // JSP 파일 이름
    }

    // 애니메이션 선택 조회
    @GetMapping("/aniListSelect")
    public String getAniListSelect(Model model) {
        List<AniListVO> aniListSelect = aniListService.getAniListSelect();
        model.addAttribute("aniListSelect", aniListSelect);
        return "aniListSelect"; // JSP 파일 이름
    }

    // 애니메이션 상세 조회
    @GetMapping("/aniDetail")
    @ResponseBody
    public ResponseEntity<AniListVO> getAniDetailView(@RequestParam String title) {
        System.out.println("aniListController test title: " + title);
        AniListVO aniDetail = aniListService.getAniDetailView(title);



        if (aniDetail != null) {
            // 조회수 증가 (필요한 경우 주석을 해제하세요)
            // aniListService.incrementAniHit(title);
            return ResponseEntity.ok(aniDetail); // 객체를 JSON으로 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 애니메이션이 없을 경우 404 상태 반환
        }


    }


    // 장르별 애니메이션 목록 조회
    @GetMapping("/aniListByGenre")
    public String getAniListByGenre(@RequestParam int anitype, Model model) {
        List<AniListVO> aniListByGenre = aniListService.getAniListByGenre(anitype);
        model.addAttribute("aniListByGenre", aniListByGenre);
        return "ani/aniList"; // JSP 파일 이름
    }

/*    // 정렬된 애니메이션 목록 조회
    @GetMapping("/aniList/sort")
    @ResponseBody
    public List<AniListVO> sortAniList(@RequestParam("sort") String sortCriteria) {
        System.out.println(sortCriteria);
        List<AniListVO> sortedAniList = aniListService.getSortedAniList(sortCriteria);
        return sortedAniList; // 정렬된 애니메이션 목록 반환
    }*/

    // 별점 추가
    @PostMapping("/addGrade")
    public ResponseEntity<Map<String, Object>> addGrade(
            @RequestBody AniListVO gradeRequest,
            @RequestHeader("Authorization") String headertoken) {

        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();

        // Authorization 헤더 확인
        if (headertoken == null || !headertoken.startsWith("Bearer ")) {
            response.put("error", "Authorization 헤더가 없거나 잘못되었습니다.");
            headers.setLocation(URI.create("/user/login")); // 리다이렉션 경로 설정
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body(response);
        }

        // 토큰 값에서 'Bearer ' 문자열 제거
        String token = headertoken.substring(7);

        if (token.isEmpty()) {
            response.put("error", "JWT 토큰이 비어 있습니다.");
            headers.setLocation(URI.create("/user/login"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body(response);
        }

        String userid;
        try {
            userid = jwtUtil.getUserIdFromToken(token); // 토큰에서 사용자 ID 추출
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "JWT 토큰 파싱 중 오류가 발생했습니다: " + e.getMessage());
            headers.setLocation(URI.create("/user/login"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body(response);
        }

        if (userid == null || userid.isEmpty()) {
            response.put("error", "유효하지 않은 JWT 토큰입니다.");
            headers.setLocation(URI.create("/user/login"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body(response);
        }

        // userid로 useridx 구하기
        Integer useridx = aniListService.getUseridx(userid);

        if (useridx == null) {

            response.put("error", "사용자 ID에 해당하는 인덱스를 찾을 수 없습니다.");
            headers.setLocation(URI.create("/user/login"));

            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body(response);
        }

        // 별점 등록 로직
        if (gradeRequest.getAni_idx() == null) {
            response.put("error", "애니메이션 인덱스가 제공되지 않았습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        if (gradeRequest.getGrade() < 1 || gradeRequest.getGrade() > 5) {
            response.put("error", "유효하지 않은 등급입니다. 1에서 5 사이의 값이어야 합니다.");
            return ResponseEntity.badRequest().body(response);
        }

        // 별점 추가 서비스 호출
        aniListService.addGrade(gradeRequest.getAni_idx(), gradeRequest.getGrade(), useridx);

        response.put("message", "별점이 등록되었습니다.");
        return ResponseEntity.ok(response);
    }






    // 좋아요 추가
    @PostMapping("/aniLike")
    public ResponseEntity<?> addLike(HttpServletRequest request, @RequestBody Map<String, Integer> body, @RequestHeader("Authorization") String Headertoken) {
        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();

        // Authorization 헤더 확인
        if (Headertoken == null || !Headertoken.startsWith("Bearer ")) {
            response.put("error", "Authorization 헤더가 없거나 잘못되었습니다.");
            headers.setLocation(URI.create("/user/login"));  // 리다이렉션 경로 설정
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);  // 303 또는 302 응답
        }
        // 토큰 값에서 'Bearer ' 문자열 제거
        String token = Headertoken.substring(7);

        if (token.isEmpty()) {
            response.put("error", "JWT 토큰이 비어 있습니다.");
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        String userid;
        try {
            userid = jwtUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID 추출
        } catch (Exception e) {
            response.put("error", "JWT 토큰 파싱 중 오류가 발생했습니다: " + e.getMessage());
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        if (userid == null || userid.isEmpty()) {
            response.put("error", "유효하지 않은 JWT 토큰입니다.");
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        // userid로 useridx 구하기
        Integer useridx = aniListService.getUseridx(userid);
        if (useridx == null) {
            response.put("error", "사용자 ID에 해당하는 인덱스를 찾을 수 없습니다.");
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        Integer ani_idx = body.get("ani_idx");
        if (ani_idx == null) {
            response.put("error", "ani_idx가 필요합니다.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            aniListService.aniLike(ani_idx, useridx); // 좋아요 추가 처리
        } catch (Exception e) {
            response.put("error", "좋아요 추가 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("message", "좋아요가 추가되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 좋아요 삭제
    @PostMapping("/removeLike")
    public ResponseEntity<?> removeLike(HttpServletRequest request, @RequestBody Map<String, Integer> body, @RequestHeader("Authorization") String Headertoken) {
        Map<String, Object> response = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();

        // Authorization 헤더 확인
        if (Headertoken == null || !Headertoken.startsWith("Bearer ")) {
            response.put("error", "Authorization 헤더가 없거나 잘못되었습니다.");
            headers.setLocation(URI.create("/user/login"));  // 리다이렉션 경로 설정
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);  // 303 또는 302 응답

        }
        // 토큰 값에서 'Bearer ' 문자열 제거
        String token = Headertoken.substring(7);

        if (token.isEmpty()) {
            response.put("error", "JWT 토큰이 비어 있습니다.");
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        String userid;
        try {
            userid = jwtUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID 추출
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "JWT 토큰 파싱 중 오류가 발생했습니다: " + e.getMessage());
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        if (userid == null || userid.isEmpty()) {
            response.put("error", "유효하지 않은 JWT 토큰입니다.");
            headers.setLocation(URI.create("/user/login"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        int useridx = aniListService.getUseridx(userid);


        Integer ani_idx = body.get("ani_idx");
        if (ani_idx == null) {
            response.put("error", "ani_idx가 필요합니다.");

            return ResponseEntity.badRequest().body(response);
        }

        aniListService.removeLike(ani_idx, useridx); // 좋아요 삭제 처리



        response.put("message", "좋아요가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/averageGrade/{ani_idx}")
    public ResponseEntity<Double> getAverageGrade(@PathVariable int ani_idx) {

        // 주어진 ani_idx에 대한 평균 별점 가져오기
        Double averageGrade = aniListService.getAverageGrade(ani_idx);

        // 평균 별점이 null일 경우 기본값 0.0으로 설정
        if (averageGrade == null) {
            averageGrade = 0.0; // 리뷰가 없으면 기본값
        }

        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }
/*

    @GetMapping("/select")
    @ResponseBody
        public List<AniListVO> getAniListfilter(@RequestParam("param1") String sortCriteria) {
            System.out.println("Sorting by: " + sortCriteria); // 정렬 기준 출력
            List<AniListVO> aniAllList = aniListService.getAniListfilter(sortCriteria); // 정렬된 애니메이션 목록 가져오기
            return aniAllList; // 애니메이션 목록 반환
        }*/



    @GetMapping("/sort")
    public ResponseEntity<List<AniListVO>> sortAniList(@RequestParam String sort) {
        List<AniListVO> sortedList;
        System.out.println(sort);
        try {
            switch (sort) {
                case "new":
                    sortedList = aniListService.getAniListSortedBynew();
                    break;
                case "title":
                    sortedList = aniListService.getAniListSortedBytitle();
                    break;
                case "popular":
                    sortedList = aniListService.getAniListSortedBypopular();
                    break;
                default:
                    return ResponseEntity.badRequest().body(null); // 기본값을 null로 반환
            }

            return ResponseEntity.ok(sortedList);
        } catch (Exception e) {
            // 로그를 남기고 적절한 에러 응답 반환
            // log.error("Error while sorting AniList: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @GetMapping("/aniDetail/{idx}")
    public ResponseEntity<Map<String, Object>> getAniDetail(@PathVariable int idx) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 유사한 애니메이션 목록 가져오기
            List<AniListVO> similarAnis = aniListService.getSimilarAnis(idx);

            // 리스트가 비어있을 경우 처리
            if (similarAnis.isEmpty()) {
                response.put("randomSimilarAnis", Collections.emptyList());
                return ResponseEntity.ok(response);
            }

            // 랜덤으로 유사한 애니메이션 5개 선택
            Collections.shuffle(similarAnis);
            List<AniListVO> randomSimilarAnis = similarAnis.stream()
                    .limit(5)
                    .collect(Collectors.toList());

            // 결과를 Map으로 만들어 반환
            response.put("randomSimilarAnis", randomSimilarAnis);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching anime details", e); // 오류 로그
            response.put("error", "Internal Server Error"); // 오류 메시지 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




}