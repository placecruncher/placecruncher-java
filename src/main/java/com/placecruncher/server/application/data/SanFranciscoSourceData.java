package com.placecruncher.server.application.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placecruncher.server.dao.SourceDao;
import com.placecruncher.server.domain.Place;
import com.placecruncher.server.domain.Source;
import com.placecruncher.server.service.SourceService;

@Component
public class SanFranciscoSourceData extends AbstractSeedData {
    @Autowired
    private SourceService sourceService;

    @Autowired
    private SourceDao sourceDao;

    public int getOrder() {
    	return SEED_USER_DATA;
    }

    public String getName() {
        return "SF Demo Data";
    }

    public boolean isRepeatable() {
    	return true;
    }

    public Collection<String> getConfigurations() {
        return Arrays.asList(new String[] {"demo"});
    }

    private Place createPlace(String name, String address, String phone, String url, String description) {
    	Place place = new Place();
    	place.setName(name);
    	place.setAddress(address);
    	place.setCity("San Francisco");
    	place.setState("CA");
    	place.setCountry("US");
    	place.setDescription(description);
    	return place;
    }

    public void populate() {
    	// each time the system starts up, so we blow away all the sources in the database.

    	String sourceUrl = "http://www.sfgate.com/cgi-bin/article.cgi?f=/c/a/2012/03/16/FD5G1NDD9Q.DTL";
    	if (sourceDao.findByUrl(sourceUrl) == null) {
        	List<Place> places = new ArrayList<Place>();
	    	Source source = sourceService.createSource("We Love Mission Food", sourceUrl );
	    	places.add(createPlace( "18 Reasons", "3674 18th St.", "(415) 568-2710", "18reasons.org", ""));
	    	places.add(createPlace( "24th Street Cheese Co.", "3893 24th St.", "(415) 821-6658", "24thstreetcheese.com", "Open daily."));
	    	places.add(createPlace( "Bar Tartine", "561 Valencia St.", " 415) 487 1600", "bartartine.com", "Dinner Tues.-Sun.; brunch weekends."));
	    	places.add(createPlace( "Beretta", "1199 Valencia St.", "(415) 695-1199", "berettasf.com", "Dinner nightly; brunch and lunch weekends."));
	    	places.add(createPlace( "Bi-Rite Creamery", "3692 18th St.", "(415) 626-5600", "biritecreamery.com", "Open daily."));
	    	places.add(createPlace( "Bi-Rite Market", "3639 18th St.", "(415) 241-9760", "biritemarket.com", "Open daily."));
	    	places.add(createPlace( "Commonwealth", "2224 Mission St.", "(415) 355-1500", "commonwealthsf.com", "Dinner nightly."));
	    	places.add(createPlace( "Contigo", "1320 Castro St.", "(415) 285-0250", "contigosf.com", "Dinner Tues.-Sun."));
	    	places.add(createPlace( "Delfina", "3621 18th St.", "(415) 552-4055", "delfinasf.com", "Dinner nightly."));
	    	places.add(createPlace( "Dianda's", "2883 Mission St.", "(415) 647-5469", "diandasbakery.com", "Open daily."));
	    	places.add(createPlace( "Dosa", "995 Valencia St.", "(415) 642-3672 or dosasf", "om", "Dinner nightly; brunch weekends."));
	    	places.add(createPlace( "Foreign Cinema", "2534 Mission St", "(415) 648-760", "foreigncinema.com", "Dinner nightly; brunch weekends."));
	    	places.add(createPlace( "Frances", "3870 17th St.", "(415) 621-3870", "frances-sf.com", "Dinner Tues.-Sun."));
	    	places.add(createPlace( "Gracias Madre", "2211 Mission St.", "(415) 683-1346", "gracias-madre.com", "Open daily."));
	    	places.add(createPlace( "Ike's Place", "3489 16th St.", "(415) 553-6888", "ilikeikesplace.com", "Open Mon-Sat."));
	    	places.add(createPlace( "La Taqueria", "2889 Mission St.", "(415) 285-7117", "Open daily", "Cash only."));
	    	places.add(createPlace( "Locanda", "557 Valencia St.", "(415) 863-6800", "locandasf.com", "Dinner nightly."));
	    	places.add(createPlace( "Lucca Ravioli Co.", "1100 Valencia St.", "(415) 647-5581", "luccaravioli.com", "Open Mon.-Sat."));
	    	places.add(createPlace( "Mission Chinese Food", "2234 Mission St", "(near 18th Street); (415) 863-280", "missionchinesefood.com", "Lunch and dinner Thurs.-Tues."));
	    	places.add(createPlace( "The Monk's Kettle", "3141 16th St.", "(415) 865-9523", "monkskettle.com", "Open daily."));
	    	places.add(createPlace( "Mozzeria", "3228 16th St.", "(415) 489-0963", "mozzeria.com", "Dinner Tues.-Sun.; brunch weekends."));
	    	places.add(createPlace( "Noe Valley Bakery", "4073 24th St.", "(415) 550-1405", "noevalleybakery.com", "Open daily."));
	    	places.add(createPlace( "Pizzeria Delfina", "3611 18th St.", "(415) 437-6800", "pizzeriadelfina.com", "Open daily."));
	    	places.add(createPlace( "Range", "842 Valencia St.", "(415) 282-8283", "rangesf.com", "Dinner nightly."));
	    	places.add(createPlace( "Regalito Rosticeria", "3481 18th St.", "(415) 503-0650", "regalitosf.com", "Dinner nightly; brunch and lunch weekends."));
	    	places.add(createPlace( "Ritual Coffee Roasters", "1026 Valencia St.", "(415) 641-1011", "ritualroasters.com", "Open daily."));
	    	places.add(createPlace( "Starbelly", "3583 16th St.", "(415) 252-7500", "starbellysf.com", "Open daily."));
	    	places.add(createPlace( "Tacolicious and Mosto Tequila", "741 Valencia St.", "(415) 626-1344", "tacolicioussf.com or mostosf.com", "Open daily."));
	    	places.add(createPlace( "Taqueria La Cumbre", "515 Valencia St.", "(415) 863-8205", "", "Open daily."));
	    	places.add(createPlace( "Tartine Bakery", "600 Guererro St.", "(415) 487-2600", "tartinebakery.com", "Open daily."));
	    	places.add(createPlace( "Udupi Palace", "1007 Valencia St.", "(415) 970-8000", "udupipalaceca.com", "Open daily. Cash only."));
	    	sourceService.addPlaces(source, places);
    	}


    	sourceUrl = "http://www.sfgate.com/cgi-bin/article.cgi?f=/c/a/2012/03/09/FDGO1NIH3L.DTL#ixzz1pZIx3vTV";
    	if (sourceDao.findByUrl(sourceUrl) == null) {
        	List<Place> places = new ArrayList<Place>();
        	Source source = sourceService.createSource("What's New: Radio Africa, El Huarache Loco, Lucky Strike", sourceUrl);
	    	places.add(createPlace( "Radio Africa", "4800 Third St.", "(415) 420-2486", "radioafricakitchen.com", "Dinner Tuesday-Saturday; lunch begins next month."));
	    	places.add(createPlace( "El Huarache Loco", "1803 Larkspur Landing Circle", "(415) 925-1403", "huaracheloco.com", "Open 8 a.m.-8 p.m. daily"));
	    	sourceService.addPlaces(source, places);
    	}

    	sourceUrl = "http://www.sfgate.com/cgi-bin/article.cgi?f=/c/a/2012/01/27/FDSS1MVHB0.DTL#ixzz1pZJxsqNw";
    	if (sourceDao.findByUrl(sourceUrl) == null) {
    		sourceService.createSource("WHAT'S NEW: New life for an old favorite", sourceUrl);
    	}

    	sourceUrl = "http://www.sfgate.com/cgi-bin/article.cgi?f=/c/a/2012/03/11/FD861NGNCN.DTL";
    	if (sourceDao.findByUrl(sourceUrl) == null) {
        	List<Place> places = new ArrayList<Place>();
    		Source source = sourceService.createSource("Finding talented chefs for 20 years", sourceUrl);
	    	places.add(createPlace( "Chez T.J.", "938 Villa St.", "(408) 964-7466", "cheztj.com", ""));
	    	places.add(createPlace( "El Paseo, 17 Throckmorton Ave.", "Mill Valley", "(415) 388-0741", "elpaseomillvalley.com", ""));
	    	places.add(createPlace( "Locanda", "557 Valencia St", "(415) 863-6800", "locandasf.com", ""));
	    	places.add(createPlace( "Outerlands", "4001 Judah St", "(415) 661-6140", "outerlandssf.com", ""));
	    	sourceService.addPlaces(source, places);
    	}


    }

}
