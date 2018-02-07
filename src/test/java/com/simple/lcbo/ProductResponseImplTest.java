package com.simple.lcbo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

@RunWith( MockitoJUnitRunner.class )
public class ProductResponseImplTest
{

	private static final String MESSAGE = "success";

	private static final int STATUS = 200;

	private final String json = "{\"status\":200,\"message\":null,\"pager\":{\"records_per_page\":20,\"total_record_count\":1,"
	                            + "\"current_page_record_count\":1,\"is_first_page\":true,\"is_final_page\":true,\"current_page\":1,"
	                            + "\"current_page_path\":\"/products?q=%22sour+harvest+saison%22\\u0026page=1\",\"next_page\":null,"
	                            + "\"next_page_path\":null,\"previous_page\":null,\"previous_page_path\":null,\"total_pages\":1,"
	                            + "\"total_pages_path\":\"/products?q=%22sour+harvest+saison%22\\u0026page=1\"},\"result\":[{\"id\":479865,"
	                            + "\"is_dead\":false,\"name\":\"Collective Arts Project Sour Harvest Saison\",\"tags\":\"collective arts project sour"
	                            + " harvest saison beer ale canada ontario brewing limited can\",\"is_discontinued\":false,\"price_in_cents\":375,"
	                            + "\"regular_price_in_cents\":375,\"limited_time_offer_savings_in_cents\":0,\"limited_time_offer_ends_on\":null,"
	                            + "\"bonus_reward_miles\":0,\"bonus_reward_miles_ends_on\":null,\"stock_type\":\"LCBO\","
	                            + "\"primary_category\":\"Beer\",\"secondary_category\":\"Ale\",\"origin\":\"Canada, Ontario\",\"package\":\"473 mL "
	                            + "can\",\"package_unit_type\":\"can\",\"package_unit_volume_in_milliliters\":473,\"total_package_units\":1,"
	                            + "\"volume_in_milliliters\":473,\"alcohol_content\":500,\"price_per_liter_of_alcohol_in_cents\":1585,"
	                            + "\"price_per_liter_in_cents\":792,\"inventory_count\":502,\"inventory_volume_in_milliliters\":237446,"
	                            + "\"inventory_price_in_cents\":188250,\"sugar_content\":null,\"producer_name\":\"Collective Arts Brewing Limited\","
	                            + "\"released_on\":null,\"has_value_added_promotion\":false,\"has_limited_time_offer\":false,"
	                            + "\"has_bonus_reward_miles\":false,\"is_seasonal\":true,\"is_vqa\":false,\"is_ocb\":false,\"is_kosher\":false,"
	                            + "\"value_added_promotion_description\":null,\"description\":null,\"serving_suggestion\":null,\"tasting_note\":null,"
	                            + "\"updated_at\":\"2018-02-06T14:21:34.476Z\",\"image_thumb_url\":\"https://dx5vpyka4lqst.cloudfront"
	                            + ".net/products/479865/images/thumb.png\",\"image_url\":\"https://dx5vpyka4lqst.cloudfront"
	                            + ".net/products/479865/images/full.jpeg\",\"varietal\":\"Saison\",\"style\":\"Medium \\u0026 Fruity\","
	                            + "\"tertiary_category\":\"Belgian Ale\",\"sugar_in_grams_per_liter\":null,\"clearance_sale_savings_in_cents\":0,"
	                            + "\"has_clearance_sale\":false,\"product_no\":479865}],\"suggestion\":null}";

	// SUT
	private ProductResponseImpl sut;

	// Collaborators
	@Mock
	private DrinkResult result;

	// Helpers
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks( this );
		sut = new ProductResponseImpl( STATUS, MESSAGE, ImmutableList.of( result ) );
	}

	@Test( expected = NullPointerException.class )
	public void test_Given_Class_When_RunningConstructorChecker_Then_Passes() throws Exception
	{
		new ProductResponseImpl( STATUS, MESSAGE, null );
	}

	@Test
	public void test_Given_Class_When_RunningEqualsVerifier_Then_Passes() throws Exception
	{
		final ProductResponseImpl response = new ProductResponseImpl( STATUS, MESSAGE, Collections.emptyList() );
		assertThat( response.equals( sut ), is( false ) );
	}

	@Test
	public void test_Given_Sut_When_GettingFieldValues_Then_ReturnCorrectValues() throws Exception
	{
		assertThat( sut.status(), is( STATUS ) );
		assertThat( sut.message(), is( MESSAGE ) );
		assertThat( sut.result(), is( ImmutableList.of( result ) ) );
	}

	@Test
	public void test_Given_RawJson_When_MappingToObject_Then_MapSuccessfully() throws Exception
	{
		final ObjectMapper objectMapper = new ObjectMapper();

		final ProductResponse response = objectMapper.readValue( json, ProductResponse.class );

		assertThat( response, isA( ProductResponse.class ) );
	}

}