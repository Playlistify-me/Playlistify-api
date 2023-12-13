package io.playlistify.api.dtos;

public record MergeNewPlaylistDto(String[] playlistIds, String newPlaylistName, String accessToken) {
}