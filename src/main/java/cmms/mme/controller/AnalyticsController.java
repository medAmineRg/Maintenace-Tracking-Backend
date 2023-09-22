package cmms.mme.controller;

import cmms.mme.dto.AnalyticsDto;
import cmms.mme.service.Analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    @GetMapping("/analytics")
    public AnalyticsDto getAnalytics() {
        return analyticsService.getAnalytics();
    }
}
