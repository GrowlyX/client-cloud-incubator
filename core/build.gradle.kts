dependencies {
    api("io.jsonwebtoken:jjwt-api:${rootProject.libs.versions.jwts.orNull}")
    api("io.jsonwebtoken:jjwt-impl:${rootProject.libs.versions.jwts.orNull}")
    api("io.jsonwebtoken:jjwt-jackson:${rootProject.libs.versions.jwts.orNull}")

    api("org.glassfish.hk2:hk2-extras:${rootProject.libs.versions.hk2.orNull}")
    api("org.glassfish.hk2:hk2-locator:${rootProject.libs.versions.hk2.orNull}")
    api("org.glassfish.hk2:hk2:${rootProject.libs.versions.hk2.orNull}")
}
