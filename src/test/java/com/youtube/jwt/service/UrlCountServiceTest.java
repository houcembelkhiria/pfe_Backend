package com.youtube.jwt.service;


import com.youtube.jwt.dao.UrlCountDao;
import com.youtube.jwt.entity.UrlCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

@ExtendWith(MockitoExtension.class)
class UrlCountServiceTest {

    @Mock
    private UrlCountDao urlCountRepository;


    @Test
    void incrementUrlCount() {
        // given
        String url = "http://test.com";
        UrlCount urlCount = new UrlCount(url, 2);

        when(urlCountRepository.findByUrl(url)).thenReturn(urlCount);

        UrlCountService urlCountService = new UrlCountService(urlCountRepository);

        // when
        urlCountService.incrementUrlCount(url);

        // then
        verify(urlCountRepository).save(urlCount);
        assertEquals(3, urlCount.getCount());
    }

}