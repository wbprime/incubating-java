package im.wangbo.bj58.ffmpeg.filter;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class ScaleFilterBuilder implements Filter.FilterBuilder {
    private final StdFilter.Builder builder = StdFilter.builder("scale");

    public void setWidth(final int v) {
        setWidthExpression(String.valueOf(v));
    }

    public void setWidthExpression(final String str) {
//        this.builder.addOption(Name.of("w"), Value.ofString(str));
    }

    public void setHeight(final int v) {
        setHeightExpression(String.valueOf(v));
    }

    public void setHeightExpression(final String str) {
//        this.builder.addOption(Name.of("h"), Value.ofString(str));
    }

    @Override
    public Filter build() {
        // TODO
        return this.builder.build();
    }
}
