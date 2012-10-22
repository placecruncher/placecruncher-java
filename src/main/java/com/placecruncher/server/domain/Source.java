package com.placecruncher.server.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.dao.SourceMetaDao;

@Entity
@Table(name="SOURCE")
@Configurable(dependencyCheck = true)
public class Source extends SuperEntity {
    private static final Logger LOGGER = Logger.getLogger(Source.class);

    private Integer id;
    private String url;
    private String name;
    private String title;
    private String description;
    private StatusEnum status = StatusEnum.OPEN;
    private MemberSourceRef memberSourceRef;
    
    private List<SourceMeta> sourceMetas = new ArrayList<SourceMeta>();
    
    @Autowired
    private SourceMetaDao sourceMetaDao;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true, updatable = false, length = Constants.URL_MAXLEN)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(nullable=false, length=Constants.NAME_MAXLEN)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length=Constants.TITLE_MAXLEN)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length=Constants.DESCRIPTION_MAXLEN)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable=false, length=Constants.ENUM_MAXLEN)
    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public enum StatusEnum {
        OPEN,
        IN_PROGRESS,
        CLOSED
    }

    @OneToOne(mappedBy = "source")
    public MemberSourceRef getMemberSourceRef() {
        return memberSourceRef;
    }

    public void setMemberSourceRef(MemberSourceRef memberSourceRef) {
        this.memberSourceRef = memberSourceRef;
    }
    
    @OneToMany(mappedBy="source", fetch=FetchType.LAZY, cascade = { CascadeType.ALL})
    public List<SourceMeta> getSourceMetas() {
        return sourceMetas;
    }

    public void setSourceMetas(List<SourceMeta> sourceMetas) {
        this.sourceMetas = sourceMetas;
    }
    
    public void scrape() {
        if (!StringUtils.isEmpty(url)) {
            Map<String, String> metaData = scrapeMetaTagsJSoup(url);
            if (!metaData.isEmpty()) {
                Set<String> keys = metaData.keySet();
                for (String key : keys) {
                    SourceMeta sourceMeta = new SourceMeta();
                    sourceMeta.setName(key);
                    sourceMeta.setValue(metaData.get(key));
                    sourceMeta.setSource(this);
                    sourceMetaDao.persist(sourceMeta);
                }
            }
        }
        
    }
    
    private static Map<String, String> scrapeMetaTagsJSoup(String url) {
        Map<String, String> metaData = new HashMap<String, String>();
        try {
            Connection connection = Jsoup.connect(url);  // NOPMD - PMD thinks this is a different Connection object.
            // don't throw exception, let's try to read it
            connection.ignoreHttpErrors(true);
            // set a legitimate user agent - some pages want it.
            connection.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:14.0) Gecko/20100101 Firefox/14.0.1");
            Document doc = connection.get();
            Elements metaElements = doc.select("meta");
            for (Element element : metaElements) {
                Attributes attributes = element.attributes();
                List<Attribute> dataSet = attributes.asList();
                
                if (!dataSet.isEmpty() && dataSet.size() == 2) {
                    String key = dataSet.get(0).getValue();
                    String value = dataSet.get(1).getValue();
                    metaData.put(key, value);
                }
                
            }
        } catch (IOException e) {
            LOGGER.error(e, e);
        }
        return metaData;
    }
}
