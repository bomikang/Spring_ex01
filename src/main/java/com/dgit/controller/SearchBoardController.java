package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtil;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Inject //or @Autowired
	private BoardService service;
	
	/*servlet-context.xml의 bean의 id값으로 주입 받아짐*/
	@Resource(name="uploadPath") 
	private String uploadPath;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info("SBoard List GET................");
		
		/*list 정보*/
		model.addAttribute("list", service.listSearch(cri));
		
		/*paging 정보*/
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}//listPage
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGet(){
		logger.info("register GET.....");
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPost(BoardVO vo, List<MultipartFile> imagefiles, RedirectAttributes rttr) throws Exception{
		System.out.println("-----------------------------------register POST.....");
		
//		upload시작
		ArrayList<String> fileNames = new ArrayList<>();
		for (MultipartFile file : imagefiles) {
			logger.info("originalName : "+file.getOriginalFilename());
			logger.info("size : "+file.getSize());
			logger.info("contentType : "+file.getContentType());
			
			String savedName = UploadFileUtil.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			
			fileNames.add(savedName);
		}
		
		String[] sFiles = fileNames.toArray(new String[fileNames.size()]);
		vo.setFiles(sFiles);
		
		service.regist(vo);
		
//		addFlashAttribute는 한번 하고 사라짐
		rttr.addFlashAttribute("result", "success"); //alert 띄워지게 하기 위해
		
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value="listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("show all list.....");
		
		model.addAttribute("list", service.listAll());
	}
	
	@RequestMapping(value="listCri", method=RequestMethod.GET)
	public String listCri(SearchCriteria cri, Model model) throws Exception{
		logger.info("show criteria list.....");
		
		model.addAttribute("list", service.listCriteria(cri));
		
		return "/sboard/list";
	}
	
	@RequestMapping(value="read", method=RequestMethod.GET)
	public void read(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		System.out.println("-----------------------------------------------------");
		logger.info("read GET=============");
		service.countViewcnt(bno);
		
		BoardVO vo =  service.read(bno);
		String[] fileNames = vo.getFiles(); //이미지 파일명
		
		model.addAttribute("boardVO", vo);
		model.addAttribute("fileNames", fileNames);
	}
	
	@RequestMapping(value="remove", method=RequestMethod.POST)
	public String remove(int bno, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr) throws Exception{
		System.out.println("-----------------------------------------------------");
		logger.info("remove POST====================");
		service.remove(bno);
		rttr.addFlashAttribute("result", "success"); //alert 띄워지게 하기 위해
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sboard/list"; //?page=xxx&perPageNum=xxx
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public void modify(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		System.out.println("-----------------------------------------------------");
		logger.info("modify GET=============");
		
		BoardVO vo =  service.read(bno);
		String[] fileNames = vo.getFiles(); //이미지 파일명
		
		model.addAttribute("boardVO", vo);
		model.addAttribute("fileNames", fileNames);
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(BoardVO vo, @ModelAttribute("cri") SearchCriteria cri, List<MultipartFile> imagefiles, RedirectAttributes rttr) throws Exception{
		System.out.println("-----------------------------------------------------");
		logger.info("modify POST=============");
		
//		upload시작
		ArrayList<String> fileNames = new ArrayList<>();
		if (imagefiles.size() >= 1) {
			for (MultipartFile file : imagefiles) {
				logger.info("originalName : "+file.getOriginalFilename());
				logger.info("size : "+file.getSize());
				logger.info("contentType : "+file.getContentType());
				
				if (file.getSize() != 0) {
					String savedName = UploadFileUtil.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
					
					fileNames.add(savedName);
				}
			}
		}
		
//		두 배열을 합칠 배열
		String[] arrAllFiles = new String[]{};
		
//		새로 업로드될 파일명등
		String[] arrImages = new String[fileNames.size()];
		for (int i = 0; i < arrImages.length; i++) {
			arrImages[i] = fileNames.get(i);
		}
		
//		삭제될 파일명들
		String[] arrForDel = vo.getFiles();
		if (arrForDel != null) {
//			하나의 배열에 합치기
			arrAllFiles = new String[arrForDel.length + arrImages.length];
			System.arraycopy(arrForDel, 0, arrAllFiles, 0, arrForDel.length);
			System.arraycopy(arrImages, 0, arrAllFiles, arrForDel.length, arrImages.length);
		}else{
			arrAllFiles = new String[arrImages.length];
			System.arraycopy(arrImages, 0, arrAllFiles, 0, arrImages.length);
		}
		
		BoardVO newBoard = service.read(vo.getBno());
		newBoard.setTitle(vo.getTitle());
		newBoard.setContent(vo.getContent());
		newBoard.setWriter(vo.getWriter());
		newBoard.setFiles(arrAllFiles); //파일명 세팅
		
		service.modify(newBoard);
		
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		
		rttr.addFlashAttribute("result", "success");
		
		return "redirect:/sboard/read" + pm.makeSearch(cri.getPage()) + "&bno="+ vo.getBno();
	}
	
	/*외부 이미지 파일 보이도록 하는 메소드*/
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName){
		InputStream inS = null;
		ResponseEntity<byte[]> entity = null;
		
		logger.info("[displayFile] fileName : "+fileName);
		
//		파일확장자만 뽑기
		String format = fileName.substring(fileName.lastIndexOf(".")+1);
		
//		패키지 com.dgit.util의 메소드를 불러와 
//		HttpHeaders에 주입
		MediaType mType = MediaUtils.getMediaType(format);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mType); //png, jpg, gif ...
		
		try {
			inS = new FileInputStream(uploadPath + "/" + fileName);
			
//			IOUtils.toByteArray(inS); : 바이트배열로 만들어주는 메소드
			entity = new ResponseEntity<>(IOUtils.toByteArray(inS), headers, HttpStatus.CREATED);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			try {
				inS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
}
