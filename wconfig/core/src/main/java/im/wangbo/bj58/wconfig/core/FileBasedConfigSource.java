package im.wangbo.bj58.wconfig.core;

import com.google.common.io.MoreFiles;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Function;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class FileBasedConfigSource<T> implements ConfigSource {
    private final Path filepath;
    private final Function<Path, T> reader;
    private final Function<T, ConfigSource> mapper;

    private static JsonObject jsonReader(final Path path) {
        final String str;
        try {
            str = MoreFiles.asCharSource(path, StandardCharsets.UTF_8).read();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file from path \"" + path.toAbsolutePath() + "\"", ex);
        }

        try (final StringReader sr = new StringReader(str)) {
            final JsonReader reader = Json.createReader(sr);
            return reader.readObject();
        }
    }

    private static Properties propertiesReader(final Path path) {
        final String str;
        try {
            str = MoreFiles.asCharSource(path, StandardCharsets.UTF_8).read();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file from path \"" + path.toAbsolutePath() + "\"", ex);
        }

        try (final StringReader sr = new StringReader(str)) {
            final Properties properties = new Properties();
            properties.load(sr);
            return properties;
        } catch (IOException ex) {
            // Should not reach here
            throw new AssertionError("Should not throw IOException when read from StringReader", ex);
        }
    }

    public static FileBasedConfigSource<?> ofJsonFile(final Path path) {
        return new FileBasedConfigSource<>(
                path, FileBasedConfigSource::jsonReader, JsonBasedConfigSource::of
        );
    }

    public static FileBasedConfigSource<?> ofPropertiesFile(final Path path) {
        return new FileBasedConfigSource<>(
                path,
                FileBasedConfigSource::propertiesReader,
                PropertiesBasedConfigSource::of
        );
    }

    public static FileBasedConfigSource<?> ofPropertiesFile(final Path path, final String keySep) {
        return new FileBasedConfigSource<>(
                path,
                FileBasedConfigSource::propertiesReader,
                p -> PropertiesBasedConfigSource.of(p, keySep)
        );
    }

    private FileBasedConfigSource(
            final Path path,
            final Function<Path, T> reader,
            final Function<T, ConfigSource> mapper
    ) {
        this.filepath = path;
        this.reader = reader;
        this.mapper = mapper;
    }

    @Override
    public JsonObject load() throws ConfigException {
        final T tmp = reader.apply(filepath);
        final ConfigSource delegate = mapper.apply(tmp);

        return delegate.load();
    }
}
