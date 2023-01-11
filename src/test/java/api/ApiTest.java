package api;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ApiTest {
    private final static String URL = "https://reqres.in/";
//
//    @Test
//    public void checkAvatarAndIdTest() {
//        List<UserData> users = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .get(URL + "api/users?page=2")
//                .then().log().all()
//                .extract().body().jsonPath().getList("data", UserData.class);
//
//        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
//
//        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
//
//        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
//        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
//        for(int i = 0; i < avatars.size(); i++){
//
//        Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
//        }
//    }

    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);


        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegText() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);

        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Register user = new Register("sydney@fife", "");
        UnSuccessReg UnsuccessReg = given()
                .body(user)
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);

        Assert.assertEquals("Missing password", UnsuccessReg.getError());
    }

//    api/unknown

    @Test
    public void sortedYearsTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);
    }

    @Test
    public void deleteUserTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }

//    @Test(priority = -5)
//    public void timeTest() {
//        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
//        UserTime user = new UserTime("morpheus", "zion resident");
//        UserTimeResponse response = given()
//                .body(user)
//                .when()
//                .put("api/users/2")
//                .then().log().all()
//                .extract().as(UserTimeResponse.class);
//        String regex = "(.{12})$";
//        String regex2 = "(.{6})$";
//        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
//
//        Assert.assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex2, ""));
//    }
}
