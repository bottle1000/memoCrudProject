package pbc.memo.controller;

import org.springframework.web.bind.annotation.*;
import pbc.memo.dto.MemoRequestDto;
import pbc.memo.dto.MemoResponseDto;
import pbc.memo.entity.Memo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos") //URL prefix
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto) {

        //식별자가 1씩 증가 하도록 만든다.
        Long memoId = memoList.isEmpty()? 1 : Collections.max(memoList.keySet()) + 1;

        //요청 받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());

        // Inmemory DB에 memo 저장
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);
    }

    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id);

        return new MemoResponseDto(memo);

    }

    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);

        memo.update(dto);

        return new MemoResponseDto(memo);
    }

    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable Long id) {
        memoList.remove(id);
    }

}
