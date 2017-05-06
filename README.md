Keepo
=====

Keepo is a small software to gather Twitch IRC chat entries and put them in a Redis storage backend.

## Usage

Compile:

```
mvn clean install
```

Launch the application with Java 8:

```
java -jar keepo-<VERSION>.jar imaqtpie
```

## Redis spec

The key-value store is organized like this:

* Key : [channel]::[username]::[uuid]
* Value : message content

## Development

1. Launch the redis storage backend: `./redis-docker.sh`
2. Hack keepo
3. Launch the application with arguments from eclipse
