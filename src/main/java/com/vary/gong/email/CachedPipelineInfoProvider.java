package com.vary.gong.email;

import com.vary.gong.go.api.GoServerApi;
import com.vary.gong.go.api.PipelineConfig;
import com.vary.gong.go.api.PipelineHistory;

import java.util.Optional;

public class CachedPipelineInfoProvider implements PipelineInfoProvider {

	private static final long MINUTES = 60 * 1000;

	private ApiCache<String, PipelineConfig> configCache;
	private ApiCache<String, PipelineHistory> historyCache;

	public CachedPipelineInfoProvider(GoServerApi api) {
		configCache = new ApiCache<>(5 * MINUTES, api::fetchPipelineConfig);
		historyCache = new ApiCache<>(0 * MINUTES, api::fetchPipelineHistory);
	}

	@Override
	public Optional<PipelineConfig> getPipelineConfig(String pipelineName) {
		return configCache.fetch(pipelineName);
	}

	@Override
	public Optional<PipelineHistory> getPipelineHistory(String pipelineName) {
		return historyCache.fetch(pipelineName);
	}

	public static void main(String[] args) {
		CachedPipelineInfoProvider p = new CachedPipelineInfoProvider(new GoServerApi("http://localhost:8153/go"));
		p.getPipelineConfig("pipeline1").ifPresent(c -> System.out.println(c.name));
		p.getPipelineConfig("pipeline2").ifPresent(c -> System.out.println(c.name));
	}
}
