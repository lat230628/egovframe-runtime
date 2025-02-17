package org.egovframe.rte.psl.data.jpa;

import org.egovframe.rte.psl.data.jpa.domain.Article;
import org.egovframe.rte.psl.data.jpa.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/context-*.xml")
@Transactional
public class CrudTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CrudTest.class);

	@Autowired
	ArticleRepository repository;

	@Test
	public void testInsert() {
		Article article = getArticle();
		article = repository.save(article);
		LOGGER.debug("Inserted articleId: {}", repository.findById(article.getArticleId()));
		assertEquals(article, repository.findById(article.getArticleId()).get());
	}

	@Test
	public void testSelectList() {
		Article[] articles = getArticleList();
		for (int i = 0; i < articles.length; i++) {
			articles[i] = repository.save(articles[i]);
		}

		LOGGER.debug("Insert {} of articles", articles.length);
		assertEquals(articles.length, repository.count());
		
		List<Article> list = (List<Article>) repository.findAll();
		for (Article article : articles) {
			LOGGER.debug("Selected article id : {}", article.getArticleId());
			assertTrue(list.contains(article));
		}
	}
	
	@Test
	public void testUpdate() {
		Article article = getArticle();
		article = repository.save(article);

		LOGGER.debug("Update article subejct: from {}", article.getSubject());
		article.setSubject("Modified");

		repository.save(article);
		LOGGER.debug("Update article subejct: to {}", article.getSubject());
		assertEquals("Modified", article.getSubject());
	}
	
	@Test
	public void testDelete() {
		Article[] articles = getArticleList();
		for (int i = 0; i < articles.length; i++) {
			articles[i] = repository.save(articles[i]);
		}

		int index = (new Random()).nextInt(articles.length);
		LOGGER.debug("Delete article id: {}", index);
		repository.delete(articles[index]);
		
		LOGGER.debug("Select Number of aricles: {}", repository.count());
		assertEquals(articles.length - 1, repository.count());
	}

	private Article[] getArticleList() {
		List<Article> articles = new ArrayList<>();
		Article article;
		for (int i = 0; i < 10; i++) {
			article = new Article();
			article.setSubject("Title " + (i+1));
			article.setContents("Sample article...");
			article.setCreatedDatetime(new Date());
			LOGGER.debug("Create article: {}, {}, {}", article.getSubject(), article.getContents(), article.getCreatedDatetime());
			articles.add(article);
		}
		return articles.toArray(new Article[0]);
	}
	
	private Article getArticle() {
		Article article = new Article();
		article.setSubject("Sample article");
		article.setContents("Sample article...");
		article.setCreatedDatetime(new Date());
		LOGGER.debug("Create article subject: {}", article.getSubject());
		return article;
	}
}
